package com.epam.esm.order.service;

import com.epam.esm.order.dto.InvoiceDto;
import com.epam.esm.order.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface OrderService {

    Page<OrderDto> findAll(Pageable pageRequest);

    OrderDto findById(int id);

    Page<OrderDto> findByUser(Integer userId, Pageable pageRequest);

    void delete(int id);

    OrderDto placeOrder(String username, Set<InvoiceDto> invoiceDtos);
}
