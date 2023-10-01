package com.epam.esm.authorizationserver.common.mapper;

public interface ModelDtoMapper<M, D> {

    M toModel(D dto);

    D toDto(M tag);
}
