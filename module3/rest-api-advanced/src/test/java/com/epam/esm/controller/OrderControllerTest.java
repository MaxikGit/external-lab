package com.epam.esm.controller;

import com.epam.esm.order.controller.OrderController;
import com.epam.esm.order.dto.OrderDto;
import com.epam.esm.order.representation.OrderAssembler;
import com.epam.esm.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
@Import(OrderAssembler.class)
class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mvc;

    @Test
    void should_ReturnJsonWithOrdersAndLinks_When_GetOrders() throws Exception {
        //given
        OrderDto orderDto1 = new OrderDto(1, BigDecimal.TEN, "2023-06-01T16:07:00.0000", 22);
        OrderDto orderDto2 = new OrderDto(2, BigDecimal.valueOf(15.5), "2023-06-01T16:10:30.0000", 23);
        when(orderService.findByUser(isNull(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(PagedModel.of(List.of(orderDto1, orderDto2),
                        new PagedModel.PageMetadata(1, 1, 2, 1)));
        //when & then
        mvc.perform(MockMvcRequestBuilders.get("/orders")
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.orderDtoList.size()").value(2))
                .andExpect(jsonPath("$._embedded.orderDtoList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.orderDtoList[0]._links.self.href").value("http://localhost/orders/1"))
                .andExpect(jsonPath("$._embedded.orderDtoList[1].id").value(2))
                .andExpect(jsonPath("$._embedded.orderDtoList[1]._links.self.href").value("http://localhost/orders/2"))
                .andExpect(jsonPath("$._links.self.href")
                        .value("http://localhost/orders?sortField=id&sortDirection=asc&offset=0&size=20{&userId}"))
                .andExpect(jsonPath("$.page").isNotEmpty());
    }

    @Test
    void should_ReturnJsonWithOrderAndLinks_When_GetById() throws Exception {
        //given
        OrderDto orderDto1 = new OrderDto(35, BigDecimal.TEN, "2023-06-01T16:07:00.0000", 155);
        when(orderService.findById(35)).thenReturn(orderDto1);
        //when & then
        mvc.perform(MockMvcRequestBuilders.get("/orders/" + 35)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.id").value(35))
                .andExpect(jsonPath("$.cost").value(10))
                .andExpect(jsonPath("$.createDate").value("2023-06-01T16:07:00.0000"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/orders/" + 35));
    }

    @Test
    void should_ReturnNoContent_When_DeleteById() throws Exception {
        //given & when & then
        mvc.perform(MockMvcRequestBuilders.delete("/orders/5"))
                .andExpect(status().isNoContent());
        verify(orderService, only()).delete(5);
    }
}