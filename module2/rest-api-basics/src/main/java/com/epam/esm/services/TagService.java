package com.epam.esm.services;

import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService {

    List<TagDto> findAll(String sortField, String sortDirection);

    TagDto findById(int id);

    TagDto findByName(String name);

    List<TagDto> find(String key, String sortField, String sortDirection);

    TagDto save(TagDto tag);

    void update(int id, TagDto tag);

    void delete(int id);
}
