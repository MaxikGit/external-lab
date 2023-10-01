package com.epam.esm.user.service;

import com.epam.esm.user.dto.SignUpDto;
import com.epam.esm.user.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<UserDto> findAll(Pageable pageRequest);

    Page<UserDto> find(String name, Pageable pageRequest);

    UserDto findById(int id);

    UserDto save(SignUpDto signUpDto);

    void update(int id, UserDto userDto);

    void delete(int id);
}
