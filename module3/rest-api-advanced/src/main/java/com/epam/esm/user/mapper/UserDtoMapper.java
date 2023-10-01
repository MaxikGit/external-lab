package com.epam.esm.user.mapper;

import com.epam.esm.user.dto.UserDto;
import com.epam.esm.user.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;

@Mapper
public interface UserDtoMapper {

    UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);

    UserModel fromDto(UserDto dto);

    UserDto toDto(UserModel tag);

    Collection<UserDto> toDto(Collection<UserModel> users);

    default PagedModel<UserDto> toDto(PagedModel<UserModel> users){
        return PagedModel.of(INSTANCE.toDto(users.getContent()), users.getMetadata());
    }
}
