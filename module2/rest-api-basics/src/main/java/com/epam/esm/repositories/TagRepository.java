package com.epam.esm.repositories;

import com.epam.esm.models.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository {

    List<Tag> findAll(String sortField, String sortDirection);

    Optional<Tag> findById(int id);

    Optional<Tag> findByName(String name);

    List<Tag> searchByName(String name, String sortField, String sortDirection);

    boolean existsId(int id);

    boolean existsName(String name);

    int save(Tag tag);

    void update(int id, Tag tag);

    void delete(int id);
}
