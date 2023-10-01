package com.epam.esm.repositories;

import com.epam.esm.models.GiftCertificate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository {

    List<GiftCertificate> findAll(String sortField, String sort);

    Optional<GiftCertificate> findById(int id);

    List<GiftCertificate> searchByName(String name, String sortField, String sort);

    List<GiftCertificate> searchByTagAndName(String tagName, String key, String sortField, String sort);

    int save(GiftCertificate certificate);

    void update(int id, GiftCertificate certificate);

    void delete(int id);

    boolean existsById(int id);
}
