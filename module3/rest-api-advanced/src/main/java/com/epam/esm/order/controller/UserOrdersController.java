package com.epam.esm.order.controller;

import com.epam.esm.order.dto.InvoiceDto;
import com.epam.esm.order.dto.OrderDto;
import com.epam.esm.order.representation.OrderAssembler;
import com.epam.esm.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/users/{userId}/orders")
@RequiredArgsConstructor
public class UserOrdersController {

    private final OrderService orderService;
    private final OrderAssembler assembler;

    @PostMapping
    public EntityModel<OrderDto> placeOrder(@PathVariable("userId") Integer userId,
                                            @Valid @RequestBody Set<InvoiceDto> invoiceDtos) {
        OrderDto dto = orderService.placeOrder(userId, invoiceDtos);
        return assembler.toEntityModel(dto);
    }
}
