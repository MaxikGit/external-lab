package com.epam.esm.common.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.common.util.HqlQueryFinisherUtil.addOrderBy;
import static com.epam.esm.common.util.HqlQueryFinisherUtil.createTotalPagesQuery;

@RequiredArgsConstructor
public abstract class BaseCrudRepositoryImpl<T> implements BaseCrudRepository<T>, PaginatedRepository {

    private final Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public PagedModel<T> findAll(String sortField, String sortDirection, int offset, int size) {
        String hqlQuery = addOrderBy(BaseHqlConstants.hqlSelectAll(entityClass), entityClass, sortField, sortDirection);
        return createPagedResults(hqlQuery, Map.of(), offset, size);
    }

    protected PagedModel<T> createPagedResults(String hqlQuery, Map<Integer, Object> queryParamContainer,
                                                int offset, int size){
        long totalElements = getTotalElements(hqlQuery, queryParamContainer);
        TypedQuery<T> query = entityManager.createQuery(hqlQuery, entityClass);
        queryParamContainer.forEach(query::setParameter);
        return PagedModel.of(query
                        .setFirstResult(offset * size)
                        .setMaxResults(size)
                        .getResultList(),
                new PagedModel.PageMetadata(size, offset, totalElements, getTotalPages(size, totalElements)));
    }

    @Override
    public Optional<T> findById(int id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public void save(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(int id) {
        entityManager.remove(entityManager.find(entityClass, id));
    }

    @Override
    public boolean existsById(int id) {
        T entity = entityManager.find(entityClass, id);
        return entity != null;
    }

    public Long getTotalElements(String hqlQuery, Map<Integer, Object> queryParams) {
        TypedQuery<Long> query = entityManager.createQuery(createTotalPagesQuery(hqlQuery), Long.class);
        queryParams.forEach(query::setParameter);
        return query.getSingleResult();
    }

    public int getTotalPages(int size, long totalElements) {
        return (int) Math.ceil((double) totalElements / size);
    }
}
