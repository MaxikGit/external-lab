package com.epam.esm.order.representation;

import com.epam.esm.order.controller.OrderController;
import com.epam.esm.order.dto.OrderDto;
import com.epam.esm.user.controller.UserController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderLinkAssembler implements RepresentationModelAssembler<OrderDto, EntityModel<OrderDto>> {

    @Override
    public EntityModel<OrderDto> toModel(OrderDto dto) {
        return EntityModel.of(dto).add(
                linkTo(methodOn(OrderController.class).getOrderById(dto.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getUserById(dto.getUserId())).withRel("owner")
        );
    }
}
