package com.epam.esm.repository;

import com.epam.esm.config.TestDatabaseConfig;
import com.epam.esm.order.model.OrderModel;
import com.epam.esm.order.repository.OrderRepository;
import com.epam.esm.user.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.hateoas.PagedModel;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {OrderRepository.class})
@Import(TestDatabaseConfig.class)
@Transactional
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
        PagedModel<OrderModel> list = orderRepository.findAll("", "", 0, 20);
        //then
        assertEquals(2, list.getMetadata().getTotalElements());
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
        //given & when
        List<OrderModel> result = orderRepository
                .findByUser(1, "id", "asc", 0, 20)
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
        OrderModel actual = orderRepository.findById(expected.getId()).orElse(null);
        assertEquals(expected, actual);
    }

    @Test
    void should_UpdateUser_When_Update() {
        //given
        OrderModel expected = new OrderModel();
        expected.setId(1);
        expected.setCost(BigDecimal.TEN);
        //when
        orderRepository.update(expected);
        //then
        OrderModel actual = orderRepository.findById(expected.getId()).orElse(null);
        assertEquals(expected, actual);
    }

    @Test
    void should_FindNull_When_Deleted() {
        //given & when
        orderRepository.delete(2);
        //then
        assertNull(orderRepository.findById(2).orElse(null));
    }

    @Test
    void should_ReturnTrue_When_ExistsId() {
        //given & when & then
        assertTrue(orderRepository.existsById(2));
    }
}