package com.epam.esm.authorizationserver.user.service;

import com.epam.esm.authorizationserver.common.exception.ResourceAlreadyExistsException;
import com.epam.esm.authorizationserver.user.model.RoleName;
import com.epam.esm.authorizationserver.user.mapper.SignUpDtoMapper;
import com.epam.esm.authorizationserver.user.dto.SignUpDto;
import com.epam.esm.authorizationserver.user.dto.UserDto;
import com.epam.esm.authorizationserver.user.mapper.UserDtoMapper;
import com.epam.esm.authorizationserver.user.model.UserModel;
import com.epam.esm.authorizationserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRemoteRegistrationService userRemoteService;

    @Override
    public UserDto save(SignUpDto userDto) {
        checkIfExistEmail(userDto.getEmail());
        UserModel user = SignUpDtoMapper.INSTANCE.toModel(userDto);
        user.setRole(RoleName.USER);
        userRepository.save(user);
        userRemoteService.save(userDto);
        return UserDtoMapper.INSTANCE.toDto(user);
    }

    private void checkIfExistEmail(String email) {
        if (userRepository.existsByEmail(email))
            throw new ResourceAlreadyExistsException(email);
    }
}
