package com.epam.esm.user.repository;

import com.epam.esm.common.repository.BaseCrudRepository;
import com.epam.esm.user.model.UserModel;
import org.springframework.hateoas.PagedModel;

public interface UserRepository extends BaseCrudRepository<UserModel> {

    PagedModel<UserModel> findByFirstLastName(String name, String sortField, String sort, int offset, int size);

    boolean existsByEmail(String email);
}
