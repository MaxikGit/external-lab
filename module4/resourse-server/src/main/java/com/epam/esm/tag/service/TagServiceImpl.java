package com.epam.esm.tag.service;

import com.epam.esm.common.exception.ResourceAlreadyExistsException;
import com.epam.esm.common.exception.ResourceNotFoundException;
import com.epam.esm.tag.dto.TagDto;
import com.epam.esm.tag.mapper.TagDtoMapper;
import com.epam.esm.tag.model.TagModel;
import com.epam.esm.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public Page<TagDto> findAll(Pageable pageRequest) {
        Page<TagModel> tags = tagRepository.findAll(pageRequest);
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
    public Page<TagDto> find(String name, Pageable pageRequest) {
        if (name.isEmpty()) {
            return findAll(pageRequest);
        }
        Page<TagModel> pagedModel = tagRepository.findAllByNameStartsWith(name, pageRequest);
        return TagDtoMapper.INSTANCE.toDto(pagedModel);
    }

    @Override
    public TagDto mostPopularTag(int userId) {
        return tagRepository.findUsersMostPopularTag(userId, Pageable.ofSize(1))
                .stream()
                .map(TagDtoMapper.INSTANCE::toDto)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(userId));
    }

    @Override
    public TagDto save(TagDto tagDto) {
        checkIfExistByName(tagDto.getName());
        tagDto.setId(null);
        TagModel tag = TagDtoMapper.INSTANCE.toModel(tagDto);
        tagRepository.save(tag);
        return TagDtoMapper.INSTANCE.toDto(tag);
    }

    @Override
    public TagDto update(int id, TagDto tagDto) {
        checkIfNotExistById(id);
        checkIfExistByName(tagDto.getName());
        tagDto.setId(id);
        TagModel tag = TagDtoMapper.INSTANCE.toModel(tagDto);
        tagRepository.save(tag);
        return TagDtoMapper.INSTANCE.toDto(tag);
    }

    @Override
    public void delete(int id) {
        TagModel tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        tagRepository.delete(tag);
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
