package com.epam.esm.mapper;

import com.epam.esm.certificate.dto.GiftCertificateDto;
import com.epam.esm.certificate.mapper.CertificateDtoMapper;
import com.epam.esm.certificate.model.GiftCertificateModel;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CertificateDtoMapperTest {

    @Test
    void should_BeEqual_When_MappingEntityToDto() {
        //given
        GiftCertificateModel certificate = GiftCertificateModel.builder()
                .id(33)
                .name("test")
                .description("testDescription")
                .price(BigDecimal.valueOf(200.55))
                .duration(10)
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .build();
        GiftCertificateDto expected = GiftCertificateDto.builder()
                .id(certificate.getId())
                .name(certificate.getName())
                .description(certificate.getDescription())
                .price(certificate.getPrice())
                .duration(certificate.getDuration())
                .createDate(certificate.getCreateDate().toString())
                .lastUpdateDate(certificate.getLastUpdateDate().toString())
                .build();
        //when
        GiftCertificateDto actual = CertificateDtoMapper.INSTANCE.toDto(certificate);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_BeEqual_When_MappingDtoToEntity() {
        //given
        GiftCertificateDto certificateDto = GiftCertificateDto.builder()
                .id(22)
                .name("testDto")
                .description("testDto")
                .price(BigDecimal.valueOf(10900.55))
                .duration(15)
                .createDate(LocalDateTime.now().toString())
                .lastUpdateDate(LocalDateTime.now().toString())
                .build();
        GiftCertificateModel expected = GiftCertificateModel.builder()
                .id(certificateDto.getId())
                .name(certificateDto.getName())
                .description(certificateDto.getDescription())
                .price(certificateDto.getPrice())
                .duration(certificateDto.getDuration())
                .createDate(convertTime(certificateDto.getCreateDate()))
                .lastUpdateDate(convertTime(certificateDto.getLastUpdateDate()))
                .build();
        //when
        GiftCertificateModel actual = CertificateDtoMapper.INSTANCE.toModel(certificateDto);
        //then
        assertEquals(expected, actual);
    }

    private static LocalDateTime convertTime(String str) {
        return LocalDateTime.parse(str, DateTimeFormatter.ISO_DATE_TIME);
    }
}

