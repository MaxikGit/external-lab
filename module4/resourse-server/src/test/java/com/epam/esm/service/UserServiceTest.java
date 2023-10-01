package com.epam.esm.service;

import com.epam.esm.common.exception.ResourceNotFoundException;
import com.epam.esm.user.dto.SignUpDto;
import com.epam.esm.user.mapper.SignUpDtoMapper;
import com.epam.esm.user.dto.UserDto;
import com.epam.esm.user.mapper.UserDtoMapper;
import com.epam.esm.user.model.UserModel;
import com.epam.esm.user.repository.UserRepository;
import com.epam.esm.user.service.UserService;
import com.epam.esm.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private UserService objectUnderTest;

    @BeforeEach
    void setUp() {
        objectUnderTest = new UserServiceImpl(userRepository);
    }

    @Test
    void should_ReturnDtoList_When_findAll() {
        //given
        Page<UserModel> input = createNewUserList();
        Page<UserDto> expected = UserDtoMapper.INSTANCE.toDto(input);
        when(userRepository.findAll(PageRequest.of(0, 20))).thenReturn(input);
        //when
        Page<UserDto> actual = objectUnderTest.findAll(PageRequest.of(0, 20));
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_returnDto_When_findById() {
        //given
        UserModel input = createNewUser(1, "");
        UserDto expected = UserDtoMapper.INSTANCE.toDto(input);
        when(userRepository.findById(1)).thenReturn(Optional.of(input));
        //when
        UserDto actual = objectUnderTest.findById(1);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_throwException_When_findByZeroId() {
        //given
        when(userRepository.findById(0)).thenReturn(Optional.empty());
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.findById(0));
    }

    @Test
    void should_ReturnDtoList_When_find() {
        //given
        Page<UserModel> input = createNewUserList();
        Page<UserDto> expectedDtoList = UserDtoMapper.INSTANCE.toDto(input);
        when(userRepository.findByNameStartingWith("key", PageRequest.of(0, 20))).thenReturn(input);
        //when
        Page<UserDto> actual = objectUnderTest.find("key", PageRequest.of(0, 20));
        //then
        assertEquals(expectedDtoList, actual);
    }

    @Test
    void should_RunShowAll_When_findEmptyName() {
        //given
        when(userRepository.findAll(PageRequest.of(0, 20))).thenReturn(createNewUserList());
        //when
        objectUnderTest.find("", PageRequest.of(0, 20));
        //then
        verify(userRepository, times(1)).findAll(PageRequest.of(0, 20));
    }

    @Test
    void should_CallRepoSave_When_Save() {
        //given
        UserModel user = createNewUser(1, "");
        SignUpDto userDto = SignUpDtoMapper.INSTANCE.toDto(user);
        when(userRepository.existsByEmail(user.getName())).thenReturn(false);
        //when
        objectUnderTest.save(userDto);
        //then
        verify(userRepository, times(1)).save(any(UserModel.class));
    }

    @Test
    void should_CallRepoUpdate_When_Update() {
        //given
        UserModel user = createNewUser(1, "");
        UserDto userDto = UserDtoMapper.INSTANCE.toDto(user);
        when(userRepository.existsById(user.getId())).thenReturn(true);
        //when
        objectUnderTest.update(1, userDto);
        //then
        verify(userRepository, times(1)).save(any(UserModel.class));
    }

    @Test
    void should_throwException_When_UpdateZeroId() {
        //given
        UserDto UserDto = UserDtoMapper.INSTANCE.toDto(createNewUser(1, ""));
        when(userRepository.existsById(0)).thenReturn(false);
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.update(0, UserDto));
    }

    @Test
    void should_delete_When_IdExist() {
        //given
        UserModel userModel = new UserModel();
        when(userRepository.findById(1)).thenReturn(Optional.of(userModel));
        //when
        objectUnderTest.delete(1);
        //then
        verify(userRepository, times(1)).delete(userModel);
    }

    @Test
    void should_ThrowException_When_DeleteZeroId() {
        //given
        when(userRepository.existsById(0)).thenReturn(false);
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.delete(0));
    }

    private static Page<UserModel> createNewUserList() {
        int size = 10;
        List<UserModel> users = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            users.add(createNewUser(i, String.valueOf(i)));
        }
        return new PageImpl<>(users, Pageable.ofSize(size), size);
    }

    private static UserModel createNewUser(Integer id, String modifier) {
        UserModel user = new UserModel();
        user.setId(id);
        user.setName("Test" + modifier);
        user.setLastName("TestTest" + modifier);
        user.setEmail("test1@test.com" + modifier);
        return user;
    }
}