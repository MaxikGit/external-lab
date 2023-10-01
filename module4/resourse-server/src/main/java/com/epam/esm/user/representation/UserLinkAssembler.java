package com.epam.esm.user.representation;

import com.epam.esm.order.controller.OrderController;
import com.epam.esm.user.controller.UserController;
import com.epam.esm.user.dto.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserLinkAssembler  implements RepresentationModelAssembler<UserDto, EntityModel<UserDto>> {

    @Override
    public EntityModel<UserDto> toModel(UserDto dto) {
        return EntityModel.of(dto).add(
                linkTo(methodOn(UserController.class).getUserById(dto.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).findOrders(dto.getId(), Pageable.ofSize(20))).withRel("orders")
        );
    }
}
