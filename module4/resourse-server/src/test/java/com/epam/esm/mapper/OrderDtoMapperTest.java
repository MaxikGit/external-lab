package com.epam.esm.mapper;

import com.epam.esm.order.dto.OrderDto;
import com.epam.esm.order.mapper.OrderDtoMapper;
import com.epam.esm.order.model.OrderModel;
import com.epam.esm.user.model.UserModel;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderDtoMapperTest {

    @Test
    void should_BeEqual_When_MappingEntityToDto() {
        //given
        UserModel user = new UserModel();
        user.setId(1);
        OrderModel model = new OrderModel(BigDecimal.TEN, user);
        model.setId(1);
        model.setCreateDate(convertString("2023-06-05T11:55:44.371"));
        OrderDto expected = new OrderDto(1, BigDecimal.TEN, "2023-06-05T11:55:44.371", user.getId());
        //when
        OrderDto actual = OrderDtoMapper.INSTANCE.toDto(model);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_BeEqual_When_MappingDtoToEntity() {
        //given
        OrderModel expected = new OrderModel(BigDecimal.TEN, null);
        expected.setId(1);
        expected.setCreateDate(convertString("2023-06-05T11:55:44.371"));
        OrderDto dto = new OrderDto(1, BigDecimal.TEN, "2023-06-05T11:55:44.371", 1);
        //when
        OrderModel actual = OrderDtoMapper.INSTANCE.toModel(dto);
        //then
        assertEquals(expected, actual);
    }

    private LocalDateTime convertString(String dateTime) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);
    }
}

