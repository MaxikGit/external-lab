package com.epam.esm.common.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public interface ModelDtoMapper<M, D> {

    M toModel(D dto);

    D toDto(M tag);

    default List<D> toDto(List<M> models){
        return models.stream().map(this::toDto).toList();
    }

    default Page<D> toDto(Page<M> models) {
        List<D> dtos = toDto(models.getContent());
        return new PageImpl<>(dtos, models.getPageable(), models.getTotalElements());
    }
}
