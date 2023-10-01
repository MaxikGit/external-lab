package com.epam.esm.tag.repository;

import com.epam.esm.tag.model.TagModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends PagingAndSortingRepository<TagModel, Integer> {

    Optional<TagModel> findByName(String name);

    Page<TagModel> findAllByNameStartsWith(String name, Pageable pageRequest);

    boolean existsByName(String name);

    @Query(value = """
            SELECT t
            FROM TagModel t
            JOIN t.certificates c
            JOIN c.orderCertificateModels ocm
            JOIN ocm.order o
            JOIN o.user u
            WHERE u.id = :userId
            GROUP BY t
            ORDER BY count(t.id) DESC
            """)
    Page<TagModel> findUsersMostPopularTag(@Param("userId") int userId, Pageable pageable);
}
