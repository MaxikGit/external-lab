package com.epam.esm.repository;

import com.epam.esm.config.TestDatabaseConfig;
import com.epam.esm.tag.model.TagModel;
import com.epam.esm.tag.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TagRepository.class)
@Import(TestDatabaseConfig.class)
@Transactional
class TagRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    void initDataBase() throws SQLException {
        ScriptUtils.executeSqlScript(dataSource.getConnection(),
                new ClassPathResource("test_data.sql"));
    }

    @Test
    void should_FindFourEntities_When_ShowAll() {
        //given & when
        Collection<TagModel> list = tagRepository.findAll("", "",  0, 20).getContent();
        //then
        assertEquals(4, list.size());
    }

    @Test
    void should_FindEntity_When_FindById() {
        //given & when
        int actual = tagRepository.findById(3).orElseThrow().getId();
        //then
        assertEquals(3, actual);
    }

    @Test
    void should_FindEntity_When_FindByName() {
        //given & when
        String actual = tagRepository.findByName("health").orElseThrow().getName();
        //then
        assertEquals("health", actual);
    }

    @Test
    void should_FindEntityWithCertainName_When_Searched() {
        //given & when
        Collection<TagModel> result = tagRepository
                .findAllByNameLike("a", "id", "asc",  0, 20)
                .getContent();
        //then
        assertTrue(result.size() > 0);
        assertTrue(result.stream().allMatch(x -> x.getName().startsWith("a")));
    }

    @Test
    void should_FindTagBirthday_When_GetMostPopularFromUser1() {
        //given & when
        String actual = tagRepository.getMostPopularTag(1).orElseThrow().getName();
        //then
        assertEquals("wedding", actual);
    }

    @Test
    void should_ReturnTrue_When_ExistsId() {
        //given & when & then
        assertTrue(tagRepository.existsById(2));
    }

    @Test
    void should_ReturnFalse_When_NotExistsId() {
        //given & when & then
        assertFalse(tagRepository.existsById(0));
    }

    @Test
    void should_AddNewEntity_When_Save() {
        //given
        TagModel tag = new TagModel(null, "new tag", new ArrayList<>());
        //when
        tagRepository.save(tag);
        //then
        TagModel tagFromDb = tagRepository.findById(tag.getId()).orElse(new TagModel());
        assertEquals(tag, tagFromDb);
    }

    @Test
    void should_UpdateEntityName_When_Update() {
        //given
        TagModel tag = tagRepository.findById(1).orElse(new TagModel());
        tag.setName("totallyNewName");
        //when
        tagRepository.update(tag);
        //then
        String actual = tagRepository.findById(1).orElseThrow().getName();
        assertEquals("totallyNewName", actual);
    }

    @Test
    void should_FindNull_When_Deleted() {
        //given
        int sizeBefore = tagRepository.findAll("id", "asc",  0, 20).getContent().size();
        //when
        tagRepository.delete(3);
        //then
        int sizeAfter = tagRepository.findAll("id", "asc",  0, 20).getContent().size();
        assertNull(tagRepository.findById(3).orElse(null));
        assertEquals(sizeBefore, sizeAfter + 1);
    }
}