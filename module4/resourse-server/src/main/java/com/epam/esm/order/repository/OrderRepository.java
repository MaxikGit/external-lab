package com.epam.esm.order.repository;

import com.epam.esm.order.model.OrderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<OrderModel, Integer> {

    Page<OrderModel> findByUserId(Integer userId, Pageable pageRequest);
}
