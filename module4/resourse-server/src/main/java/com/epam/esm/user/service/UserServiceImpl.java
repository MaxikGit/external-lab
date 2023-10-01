package com.epam.esm.user.service;

import com.epam.esm.common.exception.ResourceAlreadyExistsException;
import com.epam.esm.common.exception.ResourceNotFoundException;
import com.epam.esm.user.dto.SignUpDto;
import com.epam.esm.user.mapper.SignUpDtoMapper;
import com.epam.esm.user.dto.UserDto;
import com.epam.esm.user.mapper.UserDtoMapper;
import com.epam.esm.user.model.UserModel;
import com.epam.esm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Page<UserDto> findAll(Pageable pageRequest) {
        Page<UserModel> users = userRepository.findAll(pageRequest);
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
    public Page<UserDto> find(String name, Pageable pageRequest) {
        if (name.isEmpty()) {
            return findAll(pageRequest);
        }
        Page<UserModel> users = userRepository.findByNameStartingWith(name, pageRequest);
        return UserDtoMapper.INSTANCE.toDto(users);
    }

    @Override
    public UserDto save(SignUpDto userDto) {
        checkIfExistEmail(userDto.getEmail());
        UserModel user = SignUpDtoMapper.INSTANCE.toModel(userDto);
        userRepository.save(user);
        return UserDtoMapper.INSTANCE.toDto(user);
    }

    @Override
    public void update(int id, UserDto userDto) {
        checkIfExistId(id);
        userDto.setId(id);
        UserModel user = UserDtoMapper.INSTANCE.toModel(userDto);
        userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        UserModel userModel = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        userRepository.delete(userModel);
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
