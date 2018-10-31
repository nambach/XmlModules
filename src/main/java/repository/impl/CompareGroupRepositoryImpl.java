package repository.impl;

import entity.CompareGroup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import repository.CompareGroupRepository;
import repository.generic.impl.GenericRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class CompareGroupRepositoryImpl extends GenericRepositoryImpl<CompareGroup> implements CompareGroupRepository {

    public CompareGroupRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void updateMembers(CompareGroup group) {
        this.update(group, "members");
    }

    public List<CompareGroup> searchByNameOrAuthor(String name) {
        List<CompareGroup> list = new ArrayList<>();

        if (name == null || name.trim().equals("")) {
            return list;
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<CompareGroup> query = session.createQuery("FROM " + tableName + " b WHERE b.title LIKE :search_value OR b.authors LIKE :search_value", CompareGroup.class);

            query.setParameter("search_value", "%" + name + "%");

            list = query.list();

            session.close();
        } catch (Exception ignored) {
        }
        return list;
    }
}
