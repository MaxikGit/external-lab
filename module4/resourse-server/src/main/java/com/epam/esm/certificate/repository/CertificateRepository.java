package com.epam.esm.certificate.repository;

import com.epam.esm.certificate.model.GiftCertificateModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CertificateRepository extends CertificateCustomRepository,
        PagingAndSortingRepository<GiftCertificateModel, Integer> {

    Page<GiftCertificateModel> findByNameStartingWith(String name, Pageable pageRequest);

    List<GiftCertificateModel> findAllByIdIn(Set<Integer> ids);
}
