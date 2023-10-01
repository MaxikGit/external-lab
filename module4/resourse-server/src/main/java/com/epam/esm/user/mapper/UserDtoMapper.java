package com.epam.esm.user.mapper;

import com.epam.esm.common.mapper.ModelDtoMapper;
import com.epam.esm.user.dto.UserDto;
import com.epam.esm.user.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDtoMapper extends ModelDtoMapper<UserModel, UserDto> {

    UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);
}
