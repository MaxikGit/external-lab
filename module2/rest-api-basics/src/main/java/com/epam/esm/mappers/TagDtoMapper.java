package com.epam.esm.mappers;

import com.epam.esm.dto.TagDto;
import com.epam.esm.models.Tag;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TagDtoMapper {

    TagDtoMapper INSTANCE = Mappers.getMapper(TagDtoMapper.class);

    Tag fromDto(TagDto dto);

    TagDto toDto(Tag tag);
    List<TagDto> toDto(List<Tag> tags);
}
