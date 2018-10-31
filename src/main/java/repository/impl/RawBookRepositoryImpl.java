package repository.impl;

import entity.RawBook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import repository.RawBookRepository;
import repository.generic.impl.GenericRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class RawBookRepositoryImpl extends GenericRepositoryImpl<RawBook> implements RawBookRepository {

    public RawBookRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<RawBook> searchByNameOrAuthor(String name) {
        List<RawBook> list = new ArrayList<>();

        if (name == null || name.trim().equals("")) {
            return list;
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<RawBook> query = session.createQuery("FROM " + tableName + " b WHERE b.title LIKE :search_value OR b.authors LIKE :search_value", RawBook.class);

            query.setParameter("search_value", "%" + name + "%");

            list = query.list();

            session.close();
        } catch (Exception ignored) {
        }
        return list;
    }
}
