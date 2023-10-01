package com.epam.esm.user.controller;

import com.epam.esm.user.dto.UserDto;
import com.epam.esm.user.representation.UserAssembler;
import com.epam.esm.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.epam.esm.common.controller.DefaultRequestParams.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserAssembler assembler;

    @GetMapping
    public PagedModel<EntityModel<UserDto>> findUserByName(
            @RequestParam(value = "searchKey", defaultValue = SEARCH_KEY) String key,
            @RequestParam(value = "sortDirection", defaultValue = SORT_DIRECTION) String sortDirection,
            @RequestParam(value = "sortField", defaultValue = SORT_FIELD) String sortField,
            @RequestParam(value = "offset", defaultValue = OFFSET) int offset,
            @RequestParam(value = "size", defaultValue = SIZE) int size
    ) {
        PagedModel<UserDto> models = userService.find(key, sortField, sortDirection, offset, size);
        return assembler.toPagedModel(models, key, sortField, sortDirection, offset, size);
    }

    @GetMapping("/{id}")
    public EntityModel<UserDto> getUserById(@PathVariable("id") int id) {
        UserDto dto = userService.findById(id);
        return assembler.toEntityModel(dto);
    }
}
