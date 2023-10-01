package com.epam.esm.certificate.mapper;

import com.epam.esm.certificate.dto.GiftCertificateDto;
import com.epam.esm.certificate.model.GiftCertificateModel;
import com.epam.esm.common.mapper.ModelDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CertificateDtoMapper extends ModelDtoMapper<GiftCertificateModel, GiftCertificateDto> {

    CertificateDtoMapper INSTANCE = Mappers.getMapper(CertificateDtoMapper.class);

    GiftCertificateModel copyNotNullFields(@MappingTarget GiftCertificateModel copyTo, GiftCertificateDto copyFrom);

    default LocalDateTime convertFromDto(String string) {
        if (string == null) {
            return null;
        }
        return LocalDateTime.parse(string, DateTimeFormatter.ISO_DATE_TIME);
    }

    default String convertToDto(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.toString();
    }
}
