package com.epam.esm.authorizationserver.user.mapper;

import com.epam.esm.authorizationserver.common.mapper.ModelDtoMapper;
import com.epam.esm.authorizationserver.user.dto.SignUpDto;
import com.epam.esm.authorizationserver.user.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SignUpDtoMapper extends ModelDtoMapper<UserModel, SignUpDto> {

    SignUpDtoMapper INSTANCE = Mappers.getMapper(SignUpDtoMapper.class);

    @Override
    @Mapping(target = "password", source = "password", qualifiedByName = "encrypt")
    UserModel toModel(SignUpDto dto);

    @Named("encrypt")
    default String convertPassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }
}
