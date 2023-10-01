package com.epam.esm.repository;

import com.epam.esm.config.TestDatabaseConfig;
import com.epam.esm.user.model.UserModel;
import com.epam.esm.user.repository.UserRepository;
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
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {UserRepository.class})
@Import(TestDatabaseConfig.class)
@Transactional
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
        PagedModel<UserModel> list = userRepository.findAll("", "", 0, 20);
        //then
        assertEquals(4, list.getMetadata().getTotalElements());
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
        //given & when
        List<UserModel> result = userRepository
                .findByFirstLastName("To", "id", "asc", 0, 20)
                .getContent().stream().toList();
        //then
        assertTrue(result.stream()
                .allMatch(x -> x.getName().startsWith("To") || x.getLastName().startsWith("To")));
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
    void should_UpdateUser_When_Update() {
        //given
        UserModel expected = new UserModel();
        expected.setId(1);
        expected.setName("Test");
        expected.setLastName("TestTest");
        expected.setEmail("test1@test.com");
        //when
        userRepository.update(expected);
        //then
        UserModel actual = userRepository.findById(expected.getId()).orElse(null);
        assertEquals(expected, actual);
    }

    @Test
    void should_FindNull_When_Deleted() {
        //given & when
        userRepository.delete(3);
        //then
        assertNull(userRepository.findById(3).orElse(null));
    }

    @Test
    void should_ReturnTrue_When_ExistsId() {
        //given & when & then
        assertTrue(userRepository.existsById(3));
    }
}