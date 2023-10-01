package com.epam.esm.repository;

import com.epam.esm.tag.model.TagModel;
import com.epam.esm.tag.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
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
        List<TagModel> list = tagRepository.findAll(PageRequest.of(0, 20)).getContent();
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
        //given
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.ASC, "id");
        //when
        List<TagModel> result = tagRepository.findAllByNameStartsWith("a", pageable).getContent();
        //then
        assertTrue(result.size() > 0);
        assertTrue(result.stream().allMatch(x -> x.getName().startsWith("a")));
    }

    @Test
    void should_FindTagBirthday_When_GetMostPopularFromUser1() {
        //given & when
        String actual = tagRepository.findUsersMostPopularTag(1, Pageable.ofSize(1))
                .stream().findFirst()
                .orElseThrow().getName();
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
        tagRepository.save(tag);
        //then
        String actual = tagRepository.findById(1).orElseThrow().getName();
        assertEquals("totallyNewName", actual);
    }

    @Test
    void should_FindNull_When_Deleted() {
        //given
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.ASC, "id");
        List<TagModel> tags = tagRepository.findAll(pageable).getContent();
        int sizeBefore = tags.size();
        //when
        tagRepository.delete(tags.get(0));
        //then
        List<TagModel> tagsAfter = tagRepository.findAll(pageable).getContent();
        int sizeAfter = tagsAfter.size();
        assertNull(tagRepository.findById(1).orElse(null));
        assertEquals(sizeBefore, sizeAfter + 1);
    }
}