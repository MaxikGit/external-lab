package com.epam.esm.tag.repository;

import com.epam.esm.common.repository.BaseCrudRepositoryImpl;
import com.epam.esm.tag.model.TagModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

import static com.epam.esm.common.util.HqlQueryFinisherUtil.addOrderBy;
import static com.epam.esm.tag.repository.TagHqlConstants.*;

@Repository
public class TagRepositoryImpl extends BaseCrudRepositoryImpl<TagModel> implements TagRepository {

    public TagRepositoryImpl() {
        super(TagModel.class);
    }

    @Override
    public PagedModel<TagModel> findAllByNameLike(String name, String sortField, String sortDirection, int offset, int size) {
        String hqlQuery = addOrderBy(HQL_SEARCH_BY_NAME, TagModel.class, sortField, sortDirection);
        return createPagedResults(hqlQuery, Map.of(1, name + "%"), offset, size);
    }

    @Override
    public Optional<TagModel> getMostPopularTag(int userId) {
        return entityManager
                .createQuery(HQL_USER_POPULAR_TAG_INFO, TagModel.class)
                .setParameter(1, userId)
                .getResultStream().findFirst();
    }

    @Override
    public Optional<TagModel> findByName(String name) {
        return entityManager
                .createQuery(HQL_GET_BY_NAME, TagModel.class)
                .setParameter(1, name)
                .getResultStream().findFirst();
    }

    @Override
    public boolean existsByName(String name) {
        int count = entityManager
                .createQuery(HQL_COUNT_NAMES, Long.class)
                .setParameter(1, name)
                .getSingleResult().intValue();
        return count == 1;
    }
}
