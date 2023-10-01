package com.epam.esm.repository;

import com.epam.esm.order.model.OrderModel;
import com.epam.esm.order.repository.OrderRepository;
import com.epam.esm.user.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrdersRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void initDataBase() throws SQLException {
        ScriptUtils.executeSqlScript(dataSource.getConnection(),
                new ClassPathResource("test_data.sql"));
    }

    @Test
    void should_FindTwoEntities_When_ShowAll() {
        //given & when
        Page<OrderModel> list = orderRepository.findAll(PageRequest.of(0, 2));
        //then
        assertEquals(2, list.getTotalElements());
        assertEquals(2, list.getContent().size());
    }

    @Test
    void should_FindEntity_When_FindById() {
        //given & when
        int actual = orderRepository.findById(2).orElseThrow().getId();
        //then
        assertEquals(2, actual);
    }

    @Test
    void should_FindOrdersByUserId_When_findByUser() {
        //given
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.ASC, "id");
        //when
        List<OrderModel> result = orderRepository
                .findByUserId(1, pageable)
                .getContent().stream().toList();
        //then
        assertTrue(result.stream().allMatch(x -> x.getUser().getId().equals(1)));
        assertTrue(result.stream().allMatch(x -> x.getCost().intValue() > 240));
    }

    @Test
    void should_AddNewEntity_When_Save() {
        UserModel user = new UserModel();
        user.setId(1);
        OrderModel expected = new OrderModel();
        expected.setCost(BigDecimal.TEN);
        expected.setUser(user);
        //when
        orderRepository.save(expected);
        //then
        OrderModel actual = orderRepository.findById(expected.getId()).orElseThrow();
        assertEquals(expected, actual);
    }

    @Test
    void should_UpdateUser_When_Save() {
        //given
        OrderModel expected = orderRepository.findById(1).orElseThrow();
        expected.setCost(expected.getCost().add(BigDecimal.TEN));
        //when
        orderRepository.save(expected);
        //then
        OrderModel actual = orderRepository.findById(1).orElseThrow();
        assertEquals(expected, actual);
    }

    @Test
    void should_FindNull_When_Deleted() {
        //given
        OrderModel orderModel = orderRepository.findById(2).orElseThrow();
        //when
        orderRepository.delete(orderModel);
        //then
        assertNull(orderRepository.findById(2).orElse(null));
    }

    @Test
    void should_ReturnTrue_When_ExistsId() {
        //given & when & then
        assertTrue(orderRepository.existsById(2));
    }
}