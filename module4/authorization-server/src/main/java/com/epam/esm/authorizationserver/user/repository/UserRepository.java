package com.epam.esm.authorizationserver.user.repository;

import com.epam.esm.authorizationserver.user.model.UserModel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserModel, Integer> {

    boolean existsByEmail(String email);

    Optional<UserModel> findByEmail(String email);
}
