package repository;

import model.RawBook;
import repository.generic.GenericRepository;

import java.util.List;

public interface RawBookRepository extends GenericRepository<RawBook> {

    List<RawBook> searchByNameOrAuthor(String name);
}
