package com.epam.esm.order.controller;

import com.epam.esm.order.dto.OrderDto;
import com.epam.esm.order.representation.OrderAssembler;
import com.epam.esm.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.epam.esm.common.controller.DefaultRequestParams.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderAssembler assembler;

    @GetMapping
    public PagedModel<EntityModel<OrderDto>> findOrders(
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "sortField", defaultValue = SORT_FIELD) String sortField,
            @RequestParam(value = "sortDirection", defaultValue = SORT_DIRECTION) String sortDirection,
            @RequestParam(value = "offset", defaultValue = OFFSET) int offset,
            @RequestParam(value = "size", defaultValue = SIZE) int size
    ) {
        PagedModel<OrderDto> models = orderService.findByUser(userId, sortField, sortDirection, offset, size);
        return assembler.toPagedModel(models, userId, sortField, sortDirection, offset, size);
    }

    @GetMapping("/{id}")
    public EntityModel<OrderDto> getOrderById(@PathVariable("id") int id) {
        OrderDto dto = orderService.findById(id);
        return assembler.toEntityModel(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        orderService.delete(id);
    }
}
