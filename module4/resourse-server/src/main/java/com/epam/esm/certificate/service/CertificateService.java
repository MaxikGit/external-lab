package com.epam.esm.certificate.service;

import com.epam.esm.certificate.dto.CertificatePriceUpdateDto;
import com.epam.esm.certificate.dto.GiftCertificateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CertificateService {

    Page<GiftCertificateDto> findAll(Pageable pageRequest);

    Page<GiftCertificateDto> find(String key, List<String> tag, Pageable pageRequest);

    GiftCertificateDto findById(int id);

    GiftCertificateDto save(GiftCertificateDto certificateDto);

    GiftCertificateDto update(int id, GiftCertificateDto certificateDto);

    GiftCertificateDto partialUpdate(int id, GiftCertificateDto certificateDto);

    void delete(int id);

    GiftCertificateDto priceUpdate(CertificatePriceUpdateDto certificatePriceUpdateDto);
}
