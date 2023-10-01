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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
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
        Page<TagModel> input = createNewTagList();
        Page<TagDto> expected = TagDtoMapper.INSTANCE.toDto(input);
        when(tagRepository.findAll(any(Pageable.class))).thenReturn(input);
        //when
        Page<TagDto> actual = objectUnderTest.findAll(Pageable.ofSize(5));
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_returnDto_When_findById() {
        //given
        TagModel input = new TagModel(1, "testTag", new ArrayList<>());
        TagDto expected = new TagDto(1, "testTag");
        when(tagRepository.findById(1)).thenReturn(Optional.of(input));
        //when
        TagDto actual = objectUnderTest.findById(1);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_throwException_When_findByZeroId() {
        //given
        when(tagRepository.findById(0)).thenReturn(Optional.empty());
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.findById(0));
    }

    @Test
    void should_returnDto_When_findByName() {
        //given
        TagModel input = new TagModel(1, "testTag", new ArrayList<>());
        TagDto expected = new TagDto(1, "testTag");
        when(tagRepository.findByName("name")).thenReturn(Optional.of(input));
        //when
        TagDto actual = objectUnderTest.findByName("name");
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_throwException_When_DidntFind() {
        //given
        when(tagRepository.findByName("")).thenReturn(Optional.empty());
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.findById(0));
    }

    @Test
    void should_ReturnDtoList_When_find() {
        //given
        Page<TagModel> input = createNewTagList();
        Page<TagDto> expected = TagDtoMapper.INSTANCE.toDto(input);
        when(tagRepository.findAllByNameStartsWith("key", Pageable.ofSize(5))).thenReturn(input);
        //when
        Page<TagDto> actual = objectUnderTest.find("key", Pageable.ofSize(5));
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_RunShowAll_When_findEmptyName() {
        //given
        when(tagRepository.findAll(Pageable.ofSize(5))).thenReturn(createNewTagList());
        //when
        objectUnderTest.find("", Pageable.ofSize(5));
        //then
        verify(tagRepository, times(1)).findAll(Pageable.ofSize(5));
    }

    @Test
    void should_ReturnTagDto_When_findMostPopularTag() {
        //given
        TagDto expected = new TagDto(1, "testTag");
        TagModel input = new TagModel(1, "testTag", new ArrayList<>());
        when(tagRepository.findUsersMostPopularTag(1, Pageable.ofSize(1)))
                .thenReturn(new PageImpl<>(List.of(input)));
        //when
        TagDto actual = objectUnderTest.mostPopularTag(1);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_ThrowException_When_findMostPopularTagNoResult() {
        //given
        when(tagRepository.findUsersMostPopularTag(0, Pageable.ofSize(1))).thenReturn(Page.empty());
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.mostPopularTag(0));
    }

    @Test
    void should_CallRepoSave_When_Save() {
        //given
        TagModel tag = new TagModel(1, "testTag", new ArrayList<>());
        TagDto tagDto = new TagDto(1, "testTag");
        when(tagRepository.existsByName(tag.getName())).thenReturn(false);
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
        when(tagRepository.existsById(1)).thenReturn(true);
        when(tagRepository.existsByName("testTag")).thenReturn(false);
        //when
        objectUnderTest.update(tagDto.getId(), tagDto);
        //then
        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    void should_throwException_When_UpdateZeroId() {
        //given
        TagDto tagDto = new TagDto(1, "testTag");
        when(tagRepository.existsById(0)).thenReturn(false);
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.update(0, tagDto));
    }

    @Test
    void should_delete_When_IdExist() {
        //given
        TagModel tag = new TagModel();
        when(tagRepository.findById(1)).thenReturn(Optional.of(tag));
        //when
        objectUnderTest.delete(1);
        //then
        verify(tagRepository, times(1)).delete(tag);
    }

    @Test
    void should_ThrowException_When_DeleteZeroId() {
        //given
        when(tagRepository.findById(0)).thenReturn(Optional.empty());
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.delete(0));
    }

    private static Page<TagModel> createNewTagList() {
        Pageable pageable = PageRequest.of(1, 2);
        return new PageImpl<>(Stream
                .generate(() -> new TagModel(1, "new tag", new ArrayList<>()))
                .limit(10)
                .toList(), pageable, 10);
    }
}