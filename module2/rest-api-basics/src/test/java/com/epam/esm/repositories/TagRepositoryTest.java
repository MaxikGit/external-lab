package com.epam.esm.repositories;

import com.epam.esm.config.TestDatabaseConfig;
import com.epam.esm.models.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@ContextConfiguration(classes = TestDatabaseConfig.class)
class TagRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplateTest;
    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    void initDataBase() throws SQLException {
        ScriptUtils.executeSqlScript(jdbcTemplateTest.getDataSource().getConnection(),
                new ClassPathResource("drop_n_create_test_db.sql"));
        ScriptUtils.executeSqlScript(jdbcTemplateTest.getDataSource().getConnection(),
                new ClassPathResource("fill_out_db.sql"));
    }

    @Test
    void should_FindFourEntities_When_ShowAll() {
        //given, when
        List<Tag> list = tagRepository.findAll("", "");
        //then
        assertEquals(4, list.size());
    }

    @Test
    void should_FindEntity_When_FindById() {
        //given, when, then
        assertEquals(3, tagRepository.findById(3).orElse(new Tag()).getId());
    }

    @Test
    void should_FindEntity_When_FindByName() {
        //given, when, then
        assertEquals("anything", tagRepository.findByName("anything").orElse(new Tag()).getName());
    }

    @Test
    void should_FindEntityWithCertainName_When_Searched() {
        //given, when
        List<Tag> result = tagRepository.searchByName("a", "id", "asc");
        //then
        assertTrue(result.size() > 0 && result.stream()
                .allMatch(x -> x.getName().startsWith("a")));
    }

    @Test
    void should_ReturnTrue_When_ExistsId() {
        //given, when, then
        assertTrue(tagRepository.existsId(2));
    }

    @Test
    void should_AddNewEntity_When_Save() {
        //given
        Tag tag = new Tag(1, "new tag");
        //when
        int id = tagRepository.save(tag);
        //then
        Tag tagFromDb = tagRepository.findById(id).orElse(new Tag());
        assertEquals(tag.getName(), tagFromDb.getName());
        assertNotSame(tagFromDb, tag);
    }

    @Test
    void should_UpdateEntityName_When_Update() {
        //given
        Tag tag = tagRepository.findById(1).orElse(new Tag());
        //when
        tag.setName("totallyNewName");
        tagRepository.update(1, tag);
        //then
        assertEquals("totallyNewName", tagRepository.findById(1).orElse(new Tag()).getName());
    }

    @Test
    void should_FindNull_When_Deleted() {
        //given
        int sizeBefore = tagRepository.findAll("id", "asc").size();
        //when
        tagRepository.delete(3);
        int sizeAfter = tagRepository.findAll("id", "asc").size();
        //then
        assertNull(tagRepository.findById(3).orElse(null));
        assertEquals(sizeBefore, sizeAfter + 1);
    }
}