package com.epam.esm.authorizationserver.user.controller;

import com.epam.esm.authorizationserver.user.dto.SignUpDto;
import com.epam.esm.authorizationserver.user.dto.UserDto;
import com.epam.esm.authorizationserver.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto saveUser(@Valid @RequestBody SignUpDto signUpDto) {
        return userService.save(signUpDto);
    }
}
