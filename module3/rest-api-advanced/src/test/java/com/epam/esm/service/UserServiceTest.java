package com.epam.esm.service;

import com.epam.esm.common.exception.ResourceNotFoundException;
import com.epam.esm.user.dto.UserDto;
import com.epam.esm.user.mapper.UserDtoMapper;
import com.epam.esm.user.model.UserModel;
import com.epam.esm.user.repository.UserRepository;
import com.epam.esm.user.service.UserService;
import com.epam.esm.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.PagedModel;

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
        PagedModel<UserModel> input = createNewUserList();
        PagedModel<UserDto> expected = UserDtoMapper.INSTANCE.toDto(input);
        doReturn(input).when(userRepository).findAll(anyString(), anyString(), anyInt(), anyInt());
        //when
        PagedModel<UserDto> actual = objectUnderTest.findAll("", "", 0, 20);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_returnDto_When_findById() {
        //given
        UserModel input = createNewUser(1, "");
        UserDto expected = UserDtoMapper.INSTANCE.toDto(input);
        doReturn(Optional.of(input)).when(userRepository).findById(1);
        //when
        UserDto actual = objectUnderTest.findById(1);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_throwException_When_findByZeroId() {
        //given
        doReturn(Optional.empty()).when(userRepository).findById(0);
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.findById(0));
    }

    @Test
    void should_ReturnDtoList_When_find() {
        //given
        PagedModel<UserModel> input = createNewUserList();
        PagedModel<UserDto> expectedDtoList = UserDtoMapper.INSTANCE.toDto(input);
        doReturn(input).when(userRepository).findByFirstLastName("key", "", "", 0, 20);
        //when
        PagedModel<UserDto> actual = objectUnderTest.find("key", "", "", 0, 20);
        //then
        assertEquals(expectedDtoList, actual);
    }

    @Test
    void should_RunShowAll_When_findEmptyName() {
        //given
        doReturn(createNewUserList()).when(userRepository).findAll("", "", 0, 20);
        //when
        objectUnderTest.find("", "", "", 0, 20);
        //then
        verify(userRepository, times(1)).findAll("", "", 0, 20);
    }

    @Test
    void should_CallRepoSave_When_Save() {
        //given
        UserModel user = createNewUser(1, "");
        UserDto userDto = UserDtoMapper.INSTANCE.toDto(user);
        doReturn(false).when(userRepository).existsByEmail(user.getName());
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
        doReturn(true).when(userRepository).existsById(user.getId());
        //when
        objectUnderTest.update(1, userDto);
        //then
        verify(userRepository, times(1)).update(user);
    }

    @Test
    void should_throwException_When_UpdateZeroId() {
        //given
        UserDto UserDto = UserDtoMapper.INSTANCE.toDto(createNewUser(1, ""));
        doReturn(false).when(userRepository).existsById(0);
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.update(0, UserDto));
    }

    @Test
    void should_delete_When_IdExist() {
        //given
        doReturn(true).when(userRepository).existsById(1);
        //when
        objectUnderTest.delete(1);
        //then
        verify(userRepository, times(1)).delete(1);
    }

    @Test
    void should_ThrowException_When_DeleteZeroId() {
        //given
        doReturn(false).when(userRepository).existsById(0);
        //when & then
        assertThrows(ResourceNotFoundException.class, () -> objectUnderTest.delete(0));
    }

    private static PagedModel<UserModel> createNewUserList() {
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(1, 1, 1, 1);
        List<UserModel> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            users.add(createNewUser(i, String.valueOf(i)));
        }
        return PagedModel.of(users, metadata);
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