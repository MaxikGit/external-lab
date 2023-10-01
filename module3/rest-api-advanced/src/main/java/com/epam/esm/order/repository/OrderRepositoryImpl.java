package com.epam.esm.order.repository;

import com.epam.esm.common.repository.BaseCrudRepositoryImpl;
import com.epam.esm.order.model.OrderModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Repository;

import java.util.Map;

import static com.epam.esm.common.util.HqlQueryFinisherUtil.addOrderBy;
import static com.epam.esm.order.repository.OrderHqlConstants.HQL_SEARCH_BY_USER;

@Repository
public class OrderRepositoryImpl extends BaseCrudRepositoryImpl<OrderModel> implements OrderRepository {

    public OrderRepositoryImpl() {
        super(OrderModel.class);
    }

    @Override
    public PagedModel<OrderModel> findByUser(Integer userId, String sortField,
                                             String sortDirection, int offset, int size) {
        String hqlQuery = addOrderBy(HQL_SEARCH_BY_USER, OrderModel.class, sortField, sortDirection);
        return createPagedResults(hqlQuery, Map.of(1, userId), offset, size);
    }
}
