package com.epam.esm.order.repository;

import com.epam.esm.common.repository.BaseCrudRepository;
import com.epam.esm.order.model.OrderModel;
import org.springframework.hateoas.PagedModel;

public interface OrderRepository extends BaseCrudRepository<OrderModel> {

    PagedModel<OrderModel> findByUser(Integer userId, String sortField,
                                      String sortDirection, int offset, int size);

}
