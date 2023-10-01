package com.epam.esm.user.service;

import com.epam.esm.common.exception.ResourceAlreadyExistsException;
import com.epam.esm.common.exception.ResourceNotFoundException;
import com.epam.esm.user.dto.UserDto;
import com.epam.esm.user.mapper.UserDtoMapper;
import com.epam.esm.user.model.UserModel;
import com.epam.esm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public PagedModel<UserDto> findAll(String sortField, String sortDirection, int offset, int size) {
        PagedModel<UserModel> users = userRepository.findAll(sortField, sortDirection, offset, size);
        return UserDtoMapper.INSTANCE.toDto(users);
    }

    @Override
    public UserDto findById(int id) {
        return userRepository
                .findById(id)
                .map(UserDtoMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public PagedModel<UserDto> find(String key, String sortField, String sortDirection, int offset, int size) {
        if (key.isEmpty()) {
            return findAll(sortField, sortDirection, offset, size);
        }
        PagedModel<UserModel> users = userRepository.findByFirstLastName(key, sortField, sortDirection, offset, size);
        return UserDtoMapper.INSTANCE.toDto(users);
    }

    @Override
    public UserDto save(UserDto userDto) {
        checkIfExistEmail(userDto.getEmail());
        userDto.setId(null);
        UserModel user = UserDtoMapper.INSTANCE.fromDto(userDto);
        userRepository.save(user);
        return UserDtoMapper.INSTANCE.toDto(user);
    }

    @Override
    public void update(int id, UserDto userDto) {
        checkIfExistId(id);
        userDto.setId(id);
        UserModel user = UserDtoMapper.INSTANCE.fromDto(userDto);
        userRepository.update(user);
    }

    @Override
    public void delete(int id) {
        checkIfExistId(id);
        userRepository.delete(id);
    }

    public void checkIfExistId(int id) {
        if (!userRepository.existsById(id))
            throw new ResourceNotFoundException(id);
    }

    private void checkIfExistEmail(String email) {
        if (userRepository.existsByEmail(email))
            throw new ResourceAlreadyExistsException(email);
    }
}
