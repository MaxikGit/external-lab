package com.epam.esm.service;

import com.epam.esm.certificate.model.GiftCertificateModel;
import com.epam.esm.certificate.repository.CertificateRepository;
import com.epam.esm.common.exception.ResourceNotFoundException;
import com.epam.esm.order.dto.InvoiceDto;
import com.epam.esm.order.dto.OrderDto;
import com.epam.esm.order.mapper.OrderDtoMapper;
import com.epam.esm.order.model.OrderModel;
import com.epam.esm.order.repository.OrderRepository;
import com.epam.esm.order.service.OrderService;
import com.epam.esm.order.service.OrderServiceImpl;
import com.epam.esm.user.model.UserModel;
import com.epam.esm.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private final OrderRepository orderRepository = mock(OrderRepository.class);
    private final CertificateRepository certificateRepository = mock(CertificateRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private OrderService objectUnderTest;

    @BeforeEach
    void setUp() {
        objectUnderTest = new OrderServiceImpl(orderRepository, certificateRepository, userRepository);
    }

    @Test
    void should_ReturnDtoList_When_FindAll() {
        //given
        Page<OrderModel> input = createNewOrderList();
        Page<OrderDto> expected = OrderDtoMapper.INSTANCE.toDto(input);
        when(orderRepository.findAll(any(Pageable.class))).thenReturn(input);
        //when
        Page<OrderDto> actual = objectUnderTest.findAll(Pageable.unpaged());
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_ReturnDto_When_FindById() {
        //given
        OrderModel input = new OrderModel(BigDecimal.TEN, new UserModel());
        OrderDto expected = OrderDtoMapper.INSTANCE.toDto(input);
        when(orderRepository.findById(1)).thenReturn(Optional.of(input));
        //when
        OrderDto actual = objectUnderTest.findById(1);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_ThrowException_When_FindByZeroId() {
        //given
        when(orderRepository.findById(0)).thenReturn(Optional.empty());
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.findById(0));
    }

    @Test
    void should_ReturnDtoList_When_FindByUser() {
        //given
        Page<OrderModel> input = createNewOrderList();
        Page<OrderDto> expectedDtoList = OrderDtoMapper.INSTANCE.toDto(input);
        when(orderRepository.findByUserId(1, Pageable.ofSize(2))).thenReturn(input);
        //when
        Page<OrderDto> actual = objectUnderTest.findByUser(1, Pageable.ofSize(2));
        //then
        assertEquals(expectedDtoList, actual);
    }

    @Test
    void should_ThrowException_When_UserNotExistPlaceOrder() {
        //given
        when(userRepository.existsByEmail("email")).thenReturn(false);
        Set<InvoiceDto> invoiceSet = Set.of(new InvoiceDto(1, 2), new InvoiceDto(2, 1));
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.placeOrder("email", invoiceSet));

    }

    @Test
    void should_ThrowException_When_ItemNotExistPlaceOrder() {
        //given
        when(userRepository.existsByEmail("email@email.com")).thenReturn(true);
        when(certificateRepository.existsById(2)).thenReturn(false);
        Set<InvoiceDto> invoiceSet = Set.of(new InvoiceDto(1, 2), new InvoiceDto(2, 1));
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.placeOrder("email@email.com", invoiceSet));
    }

    @Test
    void should_CalculateCostAndCreateOrder_When_PlaceOrder() {
        //given
        UserModel user = new UserModel();
        user.setId(1);
        when(userRepository.findByEmail("email@email.com")).thenReturn(Optional.of(user));
        List<GiftCertificateModel> items = List.of(
                GiftCertificateModel.builder().id(1).price(BigDecimal.TEN).build(),
                GiftCertificateModel.builder().id(2).price(BigDecimal.TEN).build()
        );
        when(certificateRepository.findAllByIdIn(anySet())).thenReturn(items);
        Set<InvoiceDto> input = Set.of(new InvoiceDto(1, 2), new InvoiceDto(2, 1));
        //when
        OrderDto actual = objectUnderTest.placeOrder("email@email.com", input);
        //then
        assertEquals(BigDecimal.valueOf(30), actual.getCost());
        assertEquals(user.getId(), actual.getUserId());
    }

    @Test
    void should_delete_When_IdExist() {
        //given
        OrderModel orderModel = new OrderModel();
        when(orderRepository.findById(1)).thenReturn(Optional.of(orderModel));
        //when
        objectUnderTest.delete(1);
        //then
        verify(orderRepository, times(1)).delete(orderModel);
    }

    @Test
    void should_ThrowException_When_DeleteZeroId() {
        //given
        when(orderRepository.findById(0)).thenReturn(Optional.empty());
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.delete(0));
    }

    private static Page<OrderModel> createNewOrderList() {
        Pageable pageable = PageRequest.of(1, 5);
        List<OrderModel> orders = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            orders.add(new OrderModel(BigDecimal.valueOf(10 * i), new UserModel()));
            orders.get(i - 1).setId(i);
        }
        return new PageImpl<>(orders, pageable, orders.size());
    }
}