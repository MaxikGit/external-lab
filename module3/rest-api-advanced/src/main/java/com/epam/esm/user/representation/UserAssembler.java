package com.epam.esm.user.representation;

import com.epam.esm.common.util.PaginationHelper;
import com.epam.esm.order.controller.OrderController;
import com.epam.esm.user.controller.UserController;
import com.epam.esm.user.dto.UserDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import static com.epam.esm.common.controller.DefaultRequestParams.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler {

    public EntityModel<UserDto> toEntityModel(UserDto dto) {
        return EntityModel.of(dto).add(
                linkTo(methodOn(UserController.class).getUserById(dto.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).findOrders(dto.getId(), SORT_FIELD, SORT_DIRECTION,
                        Integer.parseInt(OFFSET), Integer.parseInt(SIZE))).withRel("orders")
        );
    }

    public PagedModel<EntityModel<UserDto>> toPagedModel(PagedModel<UserDto> models, String key,
                                                         String sortField, String sortDirection, int offset, int size) {
        PagedModel<EntityModel<UserDto>> result = PagedModel.of(
                models.getContent().stream().map(this::toEntityModel).toList(),
                models.getMetadata());
        Link link = linkTo(methodOn(UserController.class)
                .findUserByName(key, sortField, sortDirection, offset, size)).withSelfRel();
        PaginationHelper.insertPageLinks(result, link);
        return result;
    }
}
