package com.epam.esm.mappers;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.models.GiftCertificate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CertificateDtoMapper {

    CertificateDtoMapper INSTANCE = Mappers.getMapper(CertificateDtoMapper.class);

    GiftCertificate fromDto(GiftCertificateDto dto);

    GiftCertificate copyNotNullFields(@MappingTarget GiftCertificate copyTo, GiftCertificateDto copyFrom);

    GiftCertificateDto toDto(GiftCertificate certificate);

    List<GiftCertificateDto> toDto(List<GiftCertificate> certificates);

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
