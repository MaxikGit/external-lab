package com.epam.esm.certificate.repository;

import com.epam.esm.certificate.model.GiftCertificateModel;
import com.epam.esm.common.repository.BaseCrudRepository;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CertificateRepository extends BaseCrudRepository<GiftCertificateModel> {

    PagedModel<GiftCertificateModel> findByName(String name, String sortField, String sort, int offset, int size);

    PagedModel<GiftCertificateModel> findByTagAndName(String key, List<String>tagNames, String sortField, String sort,
                                                      int offset, int size);

    List<GiftCertificateModel> findByIds(Set<Integer> ids);
}
