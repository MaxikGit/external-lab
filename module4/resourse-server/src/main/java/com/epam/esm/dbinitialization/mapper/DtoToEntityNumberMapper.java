package com.epam.esm.dbinitialization.mapper;

import com.epam.esm.dbinitialization.dto.InitializationDto;
import com.epam.esm.dbinitialization.generator.EntityType;
import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class DtoToEntityNumberMapper {

    public static Map<EntityType, Integer> map(InitializationDto dto) {
        return Map.of(EntityType.CERTIFICATE, dto.getCertificates(),
                EntityType.TAG, dto.getTags(),
                EntityType.USER, dto.getUsers());
    }
}
