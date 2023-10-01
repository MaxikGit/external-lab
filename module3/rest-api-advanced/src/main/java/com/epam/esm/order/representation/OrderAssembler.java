package com.epam.esm.order.representation;

import com.epam.esm.common.util.PaginationHelper;
import com.epam.esm.order.controller.OrderController;
import com.epam.esm.order.dto.OrderDto;
import com.epam.esm.user.controller.UserController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderAssembler {

    public EntityModel<OrderDto> toEntityModel(OrderDto dto) {
        return EntityModel.of(dto).add(
                linkTo(methodOn(OrderController.class).getOrderById(dto.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getUserById(dto.getUserId())).withRel("owner")
        );
    }

    public PagedModel<EntityModel<OrderDto>> toPagedModel(PagedModel<OrderDto> models, Integer userId, String sortField, String sortDirection,
                                                          int offset, int size) {
        PagedModel<EntityModel<OrderDto>> result = PagedModel.of(
                models.getContent().stream().map(this::toEntityModel).toList(),
                models.getMetadata());
        Link link = linkTo(methodOn(OrderController.class)
                .findOrders(userId, sortField, sortDirection, offset, size)).withSelfRel();
        PaginationHelper.insertPageLinks(result, link);
        return result;
    }
}
