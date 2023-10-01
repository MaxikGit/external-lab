package com.epam.esm.tag.service;

import com.epam.esm.common.exception.ResourceAlreadyExistsException;
import com.epam.esm.common.exception.ResourceNotFoundException;
import com.epam.esm.tag.dto.TagDto;
import com.epam.esm.tag.mapper.TagDtoMapper;
import com.epam.esm.tag.model.TagModel;
import com.epam.esm.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public PagedModel<TagDto> findAll(String sortField, String sortDirection, int offset, int size) {
        PagedModel<TagModel> tags = tagRepository.findAll(sortField, sortDirection, offset, size);
        return TagDtoMapper.INSTANCE.toDto(tags);
    }

    @Override
    public TagDto findById(int id) {
        return tagRepository.findById(id)
                .map(TagDtoMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public TagDto findByName(String name) {
        return tagRepository.findByName(name)
                .map(TagDtoMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(name));
    }

    @Override
    public PagedModel<TagDto> find(String key, String sortField, String sortDirection, int offset, int size) {
        if (key.isEmpty()) {
            return findAll(sortField, sortDirection, offset, size);
        }
        PagedModel<TagModel> pagedModel = tagRepository.findAllByNameLike(key, sortField, sortDirection, offset, size);
        return TagDtoMapper.INSTANCE.toDto(pagedModel);
    }

    @Override
    public TagDto mostPopularTag(int userId) {
        return tagRepository
                .getMostPopularTag(userId)
                .map(TagDtoMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(null));
    }

    @Override
    public TagDto save(TagDto tagDto) {
        checkIfExistByName(tagDto.getName());
        tagDto.setId(null);
        TagModel tag = TagDtoMapper.INSTANCE.fromDto(tagDto);
        tagRepository.save(tag);
        return TagDtoMapper.INSTANCE.toDto(tag);
    }

    @Override
    public TagDto update(int id, TagDto tagDto) {
        checkIfNotExistById(id);
        checkIfExistByName(tagDto.getName());
        tagDto.setId(id);
        TagModel tag = TagDtoMapper.INSTANCE.fromDto(tagDto);
        tagRepository.update(tag);
        return TagDtoMapper.INSTANCE.toDto(tag);
    }

    @Override
    public void delete(int id) {
        checkIfNotExistById(id);
        tagRepository.delete(id);
    }

    private void checkIfNotExistById(int id) {
        if (!tagRepository.existsById(id))
            throw new ResourceNotFoundException(id);
    }

    private void checkIfExistByName(String name) {
        if (tagRepository.existsByName(name))
            throw new ResourceAlreadyExistsException(name);
    }
}
