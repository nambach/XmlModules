package repository.generic.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import repository.generic.GenericEntity;
import repository.generic.GenericRepository;

import javax.persistence.Query;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class GenericRepositoryImpl<T extends GenericEntity> implements GenericRepository<T> {

    public static SessionFactory getFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    protected String tableName;
    private Class<T> clazz;

    private void getTableName() {
        Class<T> clazz = ((Class)((ParameterizedType)this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0]);
        this.clazz = clazz;

        Table tableName = clazz.getAnnotation(Table.class);
        this.tableName = tableName.name().replaceAll("`", "");
    }

    protected SessionFactory sessionFactory;

    protected GenericRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        getTableName();
    }

    public void insert(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(entity);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    @Override
    public void insertBatch(List<T> entities) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            for (int i = 0; i < entities.size(); i++) {
                session.saveOrUpdate(entities.get(i));
                if (i % 50 == 0) { // Same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
            }

            session.getTransaction().commit();
            session.close();
        } catch (Exception ignored) {
        }
    }

    public void insertOrReplace(T toReplace) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            T entity = session.get(clazz, toReplace.getEntityId());

            if (entity != null) {
                session.delete(entity);
            }
            session.save(toReplace);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    public void update(T toReplace, String... properties) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            T entity = session.get(clazz, toReplace.getEntityId());

            if (entity == null) {
                return;
            }

            for (String property : properties) {
                try {
                    Field field = clazz.getDeclaredField(property);
                    field.setAccessible(true);
                    field.set(entity, field.get(toReplace));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                }
            }

            session.save(entity);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    public List<T> searchAll() {
        List<T> list = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            list = session.createQuery("from " + tableName, clazz).list();

            session.close();
            return list;
        } catch (Exception ignored) {
            return list;
        }
    }

    public List<T> searchAlikeColumn(Map<String, String> keyValues) {
        List<T> list = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            //e.g: key1 LIKE '%value1%' OR key2 LIKE '%value2%'
            String queryParams = keyValues.entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + " LIKE '%" + entry.getValue() + "%'")
                    .collect(Collectors.joining(" OR "));

            list = session.createQuery("from " + tableName + " where " + queryParams, clazz).list();

            session.close();
            return list;
        } catch (Exception ignored) {
            return list;
        }
    }

    public List<T> searchExactColumn(List<String> values, String columnName) {
        List<T> list = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            //e.g: id IN ('id001','id002')
            String queryParams = columnName + " in " + values.stream().map(s -> "'" + s + "'").collect(Collectors.joining(",", "(", ")"));

            list = session.createQuery("from " + tableName + " where " + queryParams, clazz).list();

            session.close();
            return list;
        } catch (Exception ignored) {
            return list;
        }
    }

    public T findById(T entity) {
        Session session = null;
        try {
            session = sessionFactory.openSession();

            return session.get(clazz, entity.getEntityId());
        } catch (Exception ignored) {
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public T delete(T toDelete) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            T entity = session.load(clazz, toDelete.getEntityId());
            if (entity != null) {
                session.delete(entity);
                session.getTransaction().commit();
            }

            session.close();
            return entity;
        } catch (Exception ignored) {
            return null;
        }
    }

    public void clearData() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Query query = session.createQuery("delete from " + tableName);
            query.executeUpdate();

            session.close();
        } catch (Exception ignored) {
        }
    }
}
