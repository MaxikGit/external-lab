package com.epam.esm.tag.mapper;

import com.epam.esm.tag.dto.TagDto;
import com.epam.esm.tag.model.TagModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;

@Mapper
public interface TagDtoMapper {

    TagDtoMapper INSTANCE = Mappers.getMapper(TagDtoMapper.class);

    TagModel fromDto(TagDto dto);

    TagDto toDto(TagModel tag);

    Collection<TagDto> toDto(Collection<TagModel> tags);

    default PagedModel<TagDto> toDto(PagedModel<TagModel> tags){
        return PagedModel.of(INSTANCE.toDto(tags.getContent()), tags.getMetadata());
    }
}
