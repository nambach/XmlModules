package repository.generic;

import java.util.List;

public interface GenericRepository<T> {

    void insert(T entity);

    void insertBatch(List<T> entities);

    void insertOrReplace(T entity);

    void update(T entity, String... properties);

    List<T> searchAll();

    T findById(T entity);

    T delete(T entity);
}
