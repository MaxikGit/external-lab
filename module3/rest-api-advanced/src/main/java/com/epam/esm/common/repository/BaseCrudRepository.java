package com.epam.esm.common.repository;

import org.springframework.hateoas.PagedModel;

import java.util.Optional;

public interface BaseCrudRepository<T> extends PaginatedRepository {

    PagedModel<T> findAll(String sortField, String sortDirection, int offset, int size);

    Optional<T> findById(int id);

    void save(T entity);

    void update(T entity);

    void delete(int id);

    boolean existsById(int id);
}
