package com.epam.esm.services;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exceptions.TagAlreadyExistsException;
import com.epam.esm.exceptions.TagNotFoundException;
import com.epam.esm.mappers.TagDtoMapper;
import com.epam.esm.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private static final TagDtoMapper TAG_DTO_MAPPER = TagDtoMapper.INSTANCE;

    @Override
    public List<TagDto> findAll(String sortField, String sortDirection) {
        return TAG_DTO_MAPPER.toDto(tagRepository.findAll(sortField, sortDirection));
    }

    @Override
    public TagDto findById(int id) {
        return TAG_DTO_MAPPER.toDto(tagRepository
                .findById(id)
                .orElseThrow(() -> new TagNotFoundException(id)));
    }

    @Override
    public TagDto findByName(String name) {
        return TAG_DTO_MAPPER.toDto(tagRepository
                .findByName(name)
                .orElseThrow(() -> new TagNotFoundException(name)));
    }

    @Override
    public List<TagDto> find(String key, String sortField, String sortDirection) {
        if (key.isEmpty()) {
            return findAll(sortField, sortDirection);
        }
        return TAG_DTO_MAPPER.toDto(tagRepository.searchByName(key, sortField, sortDirection));
    }

    @Override
    public TagDto save(TagDto tagDto) {
        if (tagRepository.existsName(tagDto.getName()))
            throw new TagAlreadyExistsException(tagDto.getName());
        int id = tagRepository.save(TAG_DTO_MAPPER.fromDto(tagDto));
        tagDto.setId(id);
        return tagDto;
    }

    @Override
    public void update(int id, TagDto tag) {
        checkIfExist(id);
        tagRepository.update(id, TAG_DTO_MAPPER.fromDto(tag));
    }

    @Override
    public void delete(int id) {
        checkIfExist(id);
        tagRepository.delete(id);
    }

    private void checkIfExist(int id) {
        if (!tagRepository.existsId(id))
            throw new TagNotFoundException(id);
    }
}
