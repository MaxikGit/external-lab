package com.epam.esm.user.repository;

import com.epam.esm.user.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserModel, Integer> {

    Page<UserModel> findByNameStartingWith(String name, Pageable pageRequest);

    boolean existsByEmail(String email);

    Optional<UserModel> findByEmail(String email);
}
