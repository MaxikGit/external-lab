package com.epam.esm.certificate.repository;

import com.epam.esm.certificate.model.GiftCertificateModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CertificateCustomRepository {

    Page<GiftCertificateModel> findByTagsAndName(String name, List<String> tagNames, Pageable pageRequest);
}
