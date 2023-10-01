package com.epam.esm.authorizationserver.user.service;

import com.epam.esm.authorizationserver.user.dto.SignUpDto;
import com.epam.esm.authorizationserver.user.dto.UserDto;

public interface UserService {

    UserDto save(SignUpDto signUpDto);
}
