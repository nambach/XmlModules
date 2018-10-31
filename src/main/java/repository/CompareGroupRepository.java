package repository;

import entity.CompareGroup;
import repository.generic.GenericRepository;

import java.util.List;

public interface CompareGroupRepository extends GenericRepository<CompareGroup> {

    void updateMembers(CompareGroup group);

    List<CompareGroup> searchByNameOrAuthor(String name);
}
