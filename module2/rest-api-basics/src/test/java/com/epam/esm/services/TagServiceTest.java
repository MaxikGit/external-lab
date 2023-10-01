package com.epam.esm.services;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exceptions.TagNotFoundException;
import com.epam.esm.mappers.TagDtoMapper;
import com.epam.esm.models.Tag;
import com.epam.esm.repositories.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TagServiceTest {

    private final TagRepository tagRepository = mock(TagRepository.class);
    private TagService objectUnderTest;

    @BeforeEach
    void setUp() {
        objectUnderTest = new TagServiceImpl(tagRepository);
    }

    @Test
    void should_ReturnDtoList_When_ShowAll() {
        //given
        List<Tag> input = createNewTagList();
        List<TagDto> expectedDtoList = TagDtoMapper.INSTANCE.toDto(input);
        doReturn(input).when(tagRepository).findAll(anyString(), anyString());
        //when, then
        assertEquals(expectedDtoList, objectUnderTest.findAll("", ""));
    }

    @Test
    void should_returnDto_When_findById() {
        //given
        Tag input = new Tag(1, "testTag");
        TagDto expected = new TagDto(1, "testTag");
        doReturn(Optional.of(input)).when(tagRepository).findById(1);
        //when, then
        assertEquals(expected, objectUnderTest.findById(1));
    }

    @Test
    void should_throwException_When_findByZeroId() {
        //given
        doReturn(Optional.empty()).when(tagRepository).findById(0);
        //when, then
        assertThrows(TagNotFoundException.class, () -> objectUnderTest.findById(0));
    }

    @Test
    void should_returnDto_When_findByName() {
        //given
        Tag input = new Tag(1, "testTag");
        TagDto expected = new TagDto(1, "testTag");
        doReturn(Optional.of(input)).when(tagRepository).findByName("name");
        //when, then
        assertEquals(expected, objectUnderTest.findByName("name"));
    }

    @Test
    void should_throwException_When_DidntFind() {
        //given
        doReturn(Optional.empty()).when(tagRepository).findByName("");
        //when, then
        assertThrows(TagNotFoundException.class, () -> objectUnderTest.findById(0));
    }

    @Test
    void should_ReturnDtoList_When_find() {
        //given
        List<Tag> input = createNewTagList();
        List<TagDto> expectedDtoList = TagDtoMapper.INSTANCE.toDto(input);
        doReturn(input).when(tagRepository).searchByName("key", "", "");
        //when, then
        assertEquals(expectedDtoList, objectUnderTest.find("key", "", ""));
    }

    @Test
    void should_RunShowAll_When_findEmptyName() {
        //given, when
        objectUnderTest.find("", "", "");
        //then
        verify(tagRepository, times(1)).findAll("", "");
    }

    @Test
    void should_ChangeTime_When_Save() {
        //given
        Tag tag = new Tag(1, "testTag");
        TagDto tagDto = new TagDto(1, "testTag");
        doReturn(false).when(tagRepository).existsName(tag.getName());
        //when
        objectUnderTest.save(tagDto);
        //then
        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    void should_ChangeUpdateTime_When_Update() {
        //given
        Tag tag = new Tag(1, "testTag");
        TagDto tagDto = new TagDto(1, "testTag");
        doReturn(true).when(tagRepository).existsId(tag.getId());
        //when
        objectUnderTest.update(tagDto.getId(), tagDto);
        //then
        verify(tagRepository, times(1)).update(tag.getId(), tag);
    }

    @Test
    void should_throwException_When_UpdateZeroId() {
        //given
        TagDto tagDto = new TagDto(1, "testTag");
        doReturn(false).when(tagRepository).existsId(0);
        //when, then
        assertThrows(TagNotFoundException.class, () -> objectUnderTest.update(0, tagDto));
    }

    @Test
    void should_delete_When_IdExist() {
        //given
        doReturn(true).when(tagRepository).existsId(1);
        //when
        objectUnderTest.delete(1);
        //then
        verify(tagRepository, times(1)).delete(1);
    }

    @Test
    void should_ThrowException_When_DeleteZeroId() {
        //given
        doReturn(false).when(tagRepository).existsId(0);
        //when, then
        assertThrows(TagNotFoundException.class, () -> objectUnderTest.delete(0));
    }

    private static List<Tag> createNewTagList() {
        return Stream.generate(() -> new Tag(1, "new tag"))
                .limit(10).toList();
    }
}