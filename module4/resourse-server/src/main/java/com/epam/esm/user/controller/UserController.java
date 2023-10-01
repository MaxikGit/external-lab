package com.epam.esm.user.controller;

import com.epam.esm.user.dto.SignUpDto;
import com.epam.esm.user.dto.UserDto;
import com.epam.esm.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.epam.esm.common.controller.DefaultRequestParams.SEARCH_KEY;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('USER')")
public class UserController {

    private final UserService userService;
    private final RepresentationModelAssembler<UserDto, EntityModel<UserDto>> userLinkAssembler;
    private final PagedResourcesAssembler<UserDto> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<EntityModel<UserDto>> findUserByName(
            @RequestParam(value = "name", defaultValue = SEARCH_KEY) String name,
            Pageable pageRequest
    ) {
        Page<UserDto> models = userService.find(name, pageRequest);
        return pagedResourcesAssembler.toModel(models, userLinkAssembler);
    }

    @GetMapping("/{id}")
    public EntityModel<UserDto> getUserById(@PathVariable("id") int id) {
        UserDto dto = userService.findById(id);
        return userLinkAssembler.toModel(dto);
    }

//    @PreAuthorize("permitAll()")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@Valid @RequestBody SignUpDto signUpDto) {
        userService.save(signUpDto);
    }
}
