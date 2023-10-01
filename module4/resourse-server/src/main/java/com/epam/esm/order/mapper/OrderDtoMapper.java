package com.epam.esm.order.mapper;

import com.epam.esm.common.mapper.ModelDtoMapper;
import com.epam.esm.order.dto.OrderDto;
import com.epam.esm.order.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderDtoMapper extends ModelDtoMapper<OrderModel, OrderDto> {

    OrderDtoMapper INSTANCE = Mappers.getMapper(OrderDtoMapper.class);

    @Override
    @Mapping(target = "userId", source = "order.user.id")
    OrderDto toDto(OrderModel order);
}
