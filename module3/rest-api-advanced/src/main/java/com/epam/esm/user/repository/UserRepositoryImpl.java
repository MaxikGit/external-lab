package com.epam.esm.user.repository;

import com.epam.esm.common.repository.BaseCrudRepositoryImpl;
import com.epam.esm.user.model.UserModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Repository;

import java.util.Map;

import static com.epam.esm.common.util.HqlQueryFinisherUtil.addOrderBy;
import static com.epam.esm.user.repository.UserHqlConstants.HQL_COUNT_EMAILS;
import static com.epam.esm.user.repository.UserHqlConstants.HQL_SEARCH_BY_NAME;

@Repository
public class UserRepositoryImpl extends BaseCrudRepositoryImpl<UserModel> implements UserRepository {

    public UserRepositoryImpl() {
        super(UserModel.class);
    }

    @Override
    public PagedModel<UserModel> findByFirstLastName(String name, String sortField, String sortDirection, int offset, int size) {
        String hqlQuery = addOrderBy(HQL_SEARCH_BY_NAME, UserModel.class, sortField, sortDirection);
        return createPagedResults(hqlQuery, Map.of(1, name + "%"), offset, size);
    }

    @Override
    public boolean existsByEmail(String email) {
        int count = entityManager
                .createQuery(HQL_COUNT_EMAILS, Long.class)
                .setParameter(1, email)
                .getSingleResult().intValue();
        return count == 1;
    }
}
