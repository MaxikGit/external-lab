package com.epam.esm.authorizationserver.user.mapper;

import com.epam.esm.authorizationserver.common.mapper.ModelDtoMapper;
import com.epam.esm.authorizationserver.user.dto.UserDto;
import com.epam.esm.authorizationserver.user.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDtoMapper extends ModelDtoMapper<UserModel, UserDto> {

    UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);
}
