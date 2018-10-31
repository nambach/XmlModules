package repository;

import entity.SuggestGroup;
import repository.generic.GenericRepository;

public interface SuggestGroupRepository extends GenericRepository<SuggestGroup> {

    void updateMembers(SuggestGroup group);
}
