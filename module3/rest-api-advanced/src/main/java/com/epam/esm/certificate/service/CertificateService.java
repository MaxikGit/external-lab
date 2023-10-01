package com.epam.esm.certificate.service;

import com.epam.esm.certificate.dto.CertificatePriceUpdateDto;
import com.epam.esm.certificate.dto.GiftCertificateDto;
import org.springframework.hateoas.PagedModel;

import java.util.List;

public interface CertificateService {

    PagedModel<GiftCertificateDto> findAll(String sortField, String sortDirection, int offset, int size);

    PagedModel<GiftCertificateDto> find(String key, List<String> tag, String sortField, String sortDirection, int offset, int size);

    GiftCertificateDto findById(int id);

    GiftCertificateDto save(GiftCertificateDto certificateDto);

    GiftCertificateDto update(int id, GiftCertificateDto certificateDto);

    GiftCertificateDto partialUpdate(int id, GiftCertificateDto certificateDto);

    void delete(int id);

    GiftCertificateDto priceUpdate(CertificatePriceUpdateDto certificatePriceUpdateDto);
}
