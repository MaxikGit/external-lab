package com.epam.esm.user.service;

import com.epam.esm.user.dto.UserDto;
import org.springframework.hateoas.PagedModel;

public interface UserService {

    PagedModel<UserDto> findAll(String sortField, String sortDirection, int offset, int size);

    UserDto findById(int id);

    PagedModel<UserDto> find(String key, String sortField, String sortDirection, int offset, int size);

    UserDto save(UserDto userDto);

    void update(int id, UserDto userDto);

    void delete(int id);
}
