package com.epam.esm.certificate.mapper;

import com.epam.esm.certificate.dto.GiftCertificateDto;
import com.epam.esm.certificate.model.GiftCertificateModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.PagedModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CertificateDtoMapper {

    CertificateDtoMapper INSTANCE = Mappers.getMapper(CertificateDtoMapper.class);

    GiftCertificateModel fromDto(GiftCertificateDto dto);

    GiftCertificateModel copyNotNullFields(@MappingTarget GiftCertificateModel copyTo, GiftCertificateDto copyFrom);

    GiftCertificateDto toDto(GiftCertificateModel certificate);

    Collection<GiftCertificateDto> toDto(Collection<GiftCertificateModel> certificates);

    default PagedModel<GiftCertificateDto> toDto(PagedModel<GiftCertificateModel> certificates){
        return PagedModel.of(INSTANCE.toDto(certificates.getContent()),
                certificates.getMetadata());
    }

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
