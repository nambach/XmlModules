package repository;

import entity.CompareGroup;
import repository.generic.GenericRepository;

public interface CompareGroupRepository extends GenericRepository<CompareGroup> {

    void updateMembers(CompareGroup group);
}
