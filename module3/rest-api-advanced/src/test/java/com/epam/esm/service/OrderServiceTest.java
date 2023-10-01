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
import org.springframework.hateoas.PagedModel;

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
        PagedModel<OrderModel> input = createNewOrderList();
        PagedModel<OrderDto> expected = OrderDtoMapper.INSTANCE.toDto(input);
        doReturn(input).when(orderRepository).findAll(anyString(), anyString(), anyInt(), anyInt());
        //when
        PagedModel<OrderDto> actual = objectUnderTest.findAll("", "", 0, 20);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_ReturnDto_When_FindById() {
        //given
        OrderModel input = new OrderModel(BigDecimal.TEN, new UserModel());
        OrderDto expected = OrderDtoMapper.INSTANCE.toDto(input);
        doReturn(Optional.of(input)).when(orderRepository).findById(1);
        //when
        OrderDto actual = objectUnderTest.findById(1);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_ThrowException_When_FindByZeroId() {
        //given
        doReturn(Optional.empty()).when(orderRepository).findById(0);
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.findById(0));
    }

    @Test
    void should_ReturnDtoList_When_FindByUser() {
        //given
        PagedModel<OrderModel> input = createNewOrderList();
        PagedModel<OrderDto> expectedDtoList = OrderDtoMapper.INSTANCE.toDto(input);
        doReturn(input).when(orderRepository).findByUser(1, "", "", 0, 20);
        //when
        PagedModel<OrderDto> actual = objectUnderTest.findByUser(1, "", "", 0, 20);
        //then
        assertEquals(expectedDtoList, actual);
    }

    @Test
    void should_ThrowException_When_UserNotExistPlaceOrder() {
        //given
        doReturn(false).when(userRepository).existsById(0);
        Set<InvoiceDto> invoiceSet = Set.of(new InvoiceDto(1, 2), new InvoiceDto(2, 1));
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.placeOrder(0, invoiceSet));

    }

    @Test
    void should_ThrowException_When_ItemNotExistPlaceOrder() {
        //given
        doReturn(true).when(userRepository).existsById(1);
        doReturn(false).when(certificateRepository).existsById(2);
        Set<InvoiceDto> invoiceSet = Set.of(new InvoiceDto(1, 2), new InvoiceDto(2, 1));
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.placeOrder(1, invoiceSet));
    }

    @Test
    void should_CalculateCostAndCreateOrder_When_PlaceOrder() {
        //given
        UserModel user = new UserModel();
        user.setId(1);
        doReturn(Optional.of(user)).when(userRepository).findById(1);
        List<GiftCertificateModel> items = List.of(
                GiftCertificateModel.builder().id(1).price(BigDecimal.TEN).build(),
                GiftCertificateModel.builder().id(2).price(BigDecimal.TEN).build()
        );
        doReturn(items).when(certificateRepository).findByIds(anySet());
        Set<InvoiceDto> input = Set.of(new InvoiceDto(1, 2), new InvoiceDto(2, 1));
        //when
        OrderDto actual = objectUnderTest.placeOrder(1, input);
        //then
        assertEquals(BigDecimal.valueOf(30), actual.getCost());
        assertEquals(user.getId(), actual.getUserId());
    }

    @Test
    void should_delete_When_IdExist() {
        //given
        doReturn(true).when(orderRepository).existsById(1);
        //when
        objectUnderTest.delete(1);
        //then
        verify(orderRepository, times(1)).delete(1);
    }

    @Test
    void should_ThrowException_When_DeleteZeroId() {
        //given
        doReturn(false).when(orderRepository).existsById(0);
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.delete(0));
    }

    private static PagedModel<OrderModel> createNewOrderList() {
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(1, 1, 1, 1);
        List<OrderModel> orders = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            orders.add(new OrderModel(BigDecimal.valueOf(10 * i), new UserModel()));
            orders.get(i - 1).setId(i);
        }
        return PagedModel.of(orders, metadata);
    }
}