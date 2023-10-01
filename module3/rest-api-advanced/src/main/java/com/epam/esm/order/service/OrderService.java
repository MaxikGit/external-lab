package com.epam.esm.order.service;

import com.epam.esm.order.dto.InvoiceDto;
import com.epam.esm.order.dto.OrderDto;
import org.springframework.hateoas.PagedModel;

import java.util.Set;

public interface OrderService {

    PagedModel<OrderDto> findAll(String sortField, String sortDirection, Integer offset, Integer size);

    OrderDto findById(int id);

    PagedModel<OrderDto> findByUser(Integer userId, String sortField,
                                    String sortDirection, int offset, int size);

    void delete(int id);

    OrderDto placeOrder(Integer userId, Set<InvoiceDto> invoiceDtos);
}
