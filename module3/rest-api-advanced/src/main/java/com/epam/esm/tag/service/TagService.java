package com.epam.esm.tag.service;

import com.epam.esm.tag.dto.TagDto;
import org.springframework.hateoas.PagedModel;

public interface TagService {

    PagedModel<TagDto> findAll(String sortField, String sortDirection, int offset, int size);

    TagDto findById(int id);

    TagDto findByName(String name);

    PagedModel<TagDto> find(String key, String sortField, String sortDirection, int offset, int size);

    TagDto mostPopularTag(int userId);

    TagDto save(TagDto tag);

    TagDto update(int id, TagDto tag);

    void delete(int id);
}
