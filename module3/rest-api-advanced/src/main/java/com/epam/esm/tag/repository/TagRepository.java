package com.epam.esm.tag.repository;

import com.epam.esm.common.repository.BaseCrudRepository;
import com.epam.esm.tag.model.TagModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends BaseCrudRepository<TagModel> {

    Optional<TagModel> getMostPopularTag(int userId);

    Optional<TagModel> findByName(String name);

    PagedModel<TagModel> findAllByNameLike(String name, String sortField, String sortDirection, int offset, int size);

    boolean existsByName(String name);
}
