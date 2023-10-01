package com.epam.esm.service;

import com.epam.esm.common.exception.ResourceNotFoundException;
import com.epam.esm.tag.dto.TagDto;
import com.epam.esm.tag.mapper.TagDtoMapper;
import com.epam.esm.tag.model.TagModel;
import com.epam.esm.tag.repository.TagRepository;
import com.epam.esm.tag.service.TagService;
import com.epam.esm.tag.service.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.PagedModel;

import java.util.ArrayList;
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
        PagedModel<TagModel> input = createNewTagList();
        PagedModel<TagDto> expected = TagDtoMapper.INSTANCE.toDto(input);
        doReturn(input).when(tagRepository).findAll(anyString(), anyString(), anyInt(), anyInt());
        //when
        PagedModel<TagDto> actual = objectUnderTest.findAll("", "", 0, 20);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_returnDto_When_findById() {
        //given
        TagModel input = new TagModel(1, "testTag", new ArrayList<>());
        TagDto expected = new TagDto(1, "testTag");
        doReturn(Optional.of(input)).when(tagRepository).findById(1);
        //when
        TagDto actual = objectUnderTest.findById(1);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_throwException_When_findByZeroId() {
        //given
        doReturn(Optional.empty()).when(tagRepository).findById(0);
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.findById(0));
    }

    @Test
    void should_returnDto_When_findByName() {
        //given
        TagModel input = new TagModel(1, "testTag", new ArrayList<>());
        TagDto expected = new TagDto(1, "testTag");
        doReturn(Optional.of(input)).when(tagRepository).findByName("name");
        //when
        TagDto actual = objectUnderTest.findByName("name");
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_throwException_When_DidntFind() {
        //given
        doReturn(Optional.empty()).when(tagRepository).findByName("");
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.findById(0));
    }

    @Test
    void should_ReturnDtoList_When_find() {
        //given
        PagedModel<TagModel> input = createNewTagList();
        PagedModel<TagDto> expected = TagDtoMapper.INSTANCE.toDto(input);
        doReturn(input).when(tagRepository).findAllByNameLike("key", "", "", 0, 20);
        //when
        PagedModel<TagDto> actual = objectUnderTest.find("key", "", "", 0, 20);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_RunShowAll_When_findEmptyName() {
        //given
        doReturn(createNewTagList()).when(tagRepository).findAll("", "", 0, 20);
        //when
        objectUnderTest.find("", "", "", 0, 20);
        //then
        verify(tagRepository, times(1)).findAll("", "", 0, 20);
    }

    @Test
    void should_ReturnTagDto_When_findMostPopularTag() {
        //given
        TagDto expected = new TagDto(1, "testTag");
        TagModel input = new TagModel(1, "testTag", new ArrayList<>());
        doReturn(Optional.of(input)).when(tagRepository).getMostPopularTag(1);
        //when
        TagDto actual = objectUnderTest.mostPopularTag(1);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_ThrowException_When_findMostPopularTagNoResult() {
        //given
        doReturn(Optional.empty()).when(tagRepository).getMostPopularTag(0);
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.mostPopularTag(0));
    }

    @Test
    void should_CallRepoSave_When_Save() {
        //given
        TagModel tag = new TagModel(1, "testTag", new ArrayList<>());
        TagDto tagDto = new TagDto(1, "testTag");
        doReturn(false).when(tagRepository).existsByName(tag.getName());
        //when
        objectUnderTest.save(tagDto);
        //then
        verify(tagRepository, times(1)).save(any(TagModel.class));
    }

    @Test
    void should_CallRepoUpdate_When_Update() {
        //given
        TagModel tag = new TagModel(1, "testTag", new ArrayList<>());
        TagDto tagDto = new TagDto(1, "testTag");
        doReturn(true).when(tagRepository).existsById(tag.getId());
        //when
        objectUnderTest.update(tagDto.getId(), tagDto);
        //then
        verify(tagRepository, times(1)).update(tag);
    }

    @Test
    void should_throwException_When_UpdateZeroId() {
        //given
        TagDto tagDto = new TagDto(1, "testTag");
        doReturn(false).when(tagRepository).existsById(0);
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.update(0, tagDto));
    }

    @Test
    void should_delete_When_IdExist() {
        //given
        doReturn(true).when(tagRepository).existsById(1);
        //when
        objectUnderTest.delete(1);
        //then
        verify(tagRepository, times(1)).delete(1);
    }

    @Test
    void should_ThrowException_When_DeleteZeroId() {
        //given
        doReturn(false).when(tagRepository).existsById(0);
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.delete(0));
    }

    private static PagedModel<TagModel> createNewTagList() {
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(1, 1, 1, 1);
        return PagedModel.of(Stream.generate(() -> new TagModel(1, "new tag", new ArrayList<>()))
                .limit(10).toList(), metadata);
    }
}