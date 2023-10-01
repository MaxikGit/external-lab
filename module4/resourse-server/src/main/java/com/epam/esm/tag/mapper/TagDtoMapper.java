package com.epam.esm.tag.mapper;

import com.epam.esm.common.mapper.ModelDtoMapper;
import com.epam.esm.tag.dto.TagDto;
import com.epam.esm.tag.model.TagModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagDtoMapper extends ModelDtoMapper<TagModel, TagDto> {

    TagDtoMapper INSTANCE = Mappers.getMapper(TagDtoMapper.class);
}
