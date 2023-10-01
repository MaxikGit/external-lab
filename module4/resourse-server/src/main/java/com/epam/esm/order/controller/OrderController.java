package com.epam.esm.order.controller;

import com.epam.esm.order.dto.InvoiceDto;
import com.epam.esm.order.dto.OrderDto;
import com.epam.esm.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('USER')")
public class OrderController {

    private final OrderService orderService;
    private final RepresentationModelAssembler<OrderDto, EntityModel<OrderDto>> orderLinkAssembler;
    private final PagedResourcesAssembler<OrderDto> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<EntityModel<OrderDto>> findOrders(@RequestParam(value = "userId", required = false) Integer userId,
                                                        Pageable pageRequest) {
        Page<OrderDto> models = orderService.findByUser(userId, pageRequest);
        return pagedResourcesAssembler.toModel(models, orderLinkAssembler);
    }

    @GetMapping("/{id}")
    public EntityModel<OrderDto> getOrderById(@PathVariable("id") int id) {
        OrderDto dto = orderService.findById(id);
        return orderLinkAssembler.toModel(dto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<OrderDto> placeOrder(Authentication auth, @Valid @RequestBody Set<InvoiceDto> invoiceDtos) {
        OrderDto dto = orderService.placeOrder(auth.getName(), invoiceDtos);
        return orderLinkAssembler.toModel(dto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        orderService.delete(id);
    }
}
