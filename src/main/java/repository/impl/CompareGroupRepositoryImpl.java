package repository.impl;

import entity.CompareGroup;
import org.hibernate.SessionFactory;
import repository.CompareGroupRepository;
import repository.generic.impl.GenericRepositoryImpl;

public class CompareGroupRepositoryImpl extends GenericRepositoryImpl<CompareGroup> implements CompareGroupRepository {

    public CompareGroupRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void updateMembers(CompareGroup group) {
        this.update(group, "members");
    }
}
