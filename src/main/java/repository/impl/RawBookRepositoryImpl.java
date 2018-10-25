package repository.impl;

import model.RawBook;
import org.hibernate.SessionFactory;
import repository.generic.impl.GenericRepositoryImpl;

public class RawBookRepositoryImpl extends GenericRepositoryImpl<RawBook> {

    public RawBookRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
