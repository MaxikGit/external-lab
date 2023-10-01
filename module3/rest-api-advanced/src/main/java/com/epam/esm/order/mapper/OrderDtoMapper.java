package com.epam.esm.order.mapper;

import com.epam.esm.order.dto.OrderDto;
import com.epam.esm.order.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;

@Mapper
public interface OrderDtoMapper {

    OrderDtoMapper INSTANCE = Mappers.getMapper(OrderDtoMapper.class);

    OrderModel fromDto(OrderDto dto);

    @Mapping(target = "userId", source = "order.user.id")
    OrderDto toDto(OrderModel order);

    Collection<OrderDto> toDto(Collection<OrderModel> users);

    default PagedModel<OrderDto> toDto(PagedModel<OrderModel> users) {
        return PagedModel.of(INSTANCE.toDto(users.getContent()), users.getMetadata());
    }
}
