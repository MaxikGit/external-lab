package com.epam.esm.tag.service;

import com.epam.esm.tag.dto.TagDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {

    Page<TagDto> findAll(Pageable pageRequest);

    TagDto findById(int id);

    TagDto findByName(String name);

    Page<TagDto> find(String name, Pageable pageRequest);

    TagDto mostPopularTag(int userId);

    TagDto save(TagDto tag);

    TagDto update(int id, TagDto tag);

    void delete(int id);
}
