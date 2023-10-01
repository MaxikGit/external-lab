package com.epam.esm.repository;

import com.epam.esm.user.model.UserModel;
import com.epam.esm.user.repository.UserRepository;
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
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void initDataBase() throws SQLException {
        ScriptUtils.executeSqlScript(dataSource.getConnection(),
                new ClassPathResource("test_data.sql"));
    }

    @Test
    void should_FindFourEntities_When_ShowAll() {
        //given & when
        Page<UserModel> list = userRepository.findAll(PageRequest.of(0, 20));
        //then
        assertEquals(4, list.getTotalElements());
        assertEquals(4, list.getContent().size());
    }

    @Test
    void should_FindEntity_When_FindById() {
        //given & when
        int actual = userRepository.findById(3).orElse(new UserModel()).getId();
        //then
        assertEquals(3, actual);
    }

    @Test
    void should_FindEntityWithCertainNameOrLastName_When_Searched() {
        //given
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.ASC, "id");
        //when
        List<UserModel> result = userRepository.findByNameStartingWith("To", pageable).getContent();
        //then
        assertTrue(result.stream()
                .allMatch(x -> x.getName().startsWith("To")));
    }

    @Test
    void should_AddNewEntity_When_Save() {
        //given
        UserModel expected = new UserModel();
        expected.setName("Test");
        expected.setLastName("TestTest");
        expected.setEmail("test1@test.com");
        //when
        userRepository.save(expected);
        //then
        UserModel actual = userRepository.findById(expected.getId()).orElseThrow();
        assertEquals(expected, actual);
    }

    @Test
    void should_UpdateUser_When_Save() {
        //given
        UserModel actual = userRepository.findById(1).orElseThrow();
        actual.setName("Test");
        actual.setLastName("TestTest");
        actual.setEmail("test1@test.com");
        //when
        userRepository.save(actual);
        //then
        UserModel expected1 = userRepository.findById(actual.getId()).orElseThrow();
        assertEquals(expected1, actual);
    }

    @Test
    void should_FindNull_When_Deleted() {
        //given
        UserModel user = userRepository.findById(1).orElseThrow();
        //when
        userRepository.delete(user);
        //then
        assertNull(userRepository.findById(1).orElse(null));
    }

    @Test
    void should_ReturnTrue_When_ExistsId() {
        //given & when & then
        assertTrue(userRepository.existsById(3));
    }
}