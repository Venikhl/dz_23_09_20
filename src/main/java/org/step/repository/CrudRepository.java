package org.step.repository;

import java.util.List;

public interface CrudRepository<T> {

    T save(T t);

    List<T> findAll();

    void delete(Long id);
}
