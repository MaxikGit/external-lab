package com.epam.esm.services;

import com.epam.esm.dto.GiftCertificateDto;

import java.util.List;

public interface CertificateService {

    List<GiftCertificateDto> findAll(String sortField, String sortDirection);

    GiftCertificateDto findById(int id);

    List<GiftCertificateDto> find(String tag, String key, String sortField, String sortDirection);

    GiftCertificateDto save(GiftCertificateDto certificateDto);

    void update(int id, GiftCertificateDto certificateDto);

    GiftCertificateDto partialUpdate(int id, GiftCertificateDto certificateDto);

    void delete(int id);
}
