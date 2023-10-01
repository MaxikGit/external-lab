package com.epam.esm.user.mapper;

import com.epam.esm.common.mapper.ModelDtoMapper;
import com.epam.esm.user.dto.SignUpDto;
import com.epam.esm.user.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SignUpDtoMapper extends ModelDtoMapper<UserModel, SignUpDto> {

    SignUpDtoMapper INSTANCE = Mappers.getMapper(SignUpDtoMapper.class);
}
