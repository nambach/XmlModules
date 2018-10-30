package repository.impl;

import entity.SuggestGroup;
import org.hibernate.SessionFactory;
import repository.SuggestGroupRepository;
import repository.generic.impl.GenericRepositoryImpl;

public class SuggestGroupRepositoryImpl extends GenericRepositoryImpl<SuggestGroup> implements SuggestGroupRepository {

    public SuggestGroupRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void updateMembers(SuggestGroup group) {
        this.update(group, "members");
    }
}
