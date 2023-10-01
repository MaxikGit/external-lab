package com.epam.esm.repositories;

import com.epam.esm.models.TagGiftCertificate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagCertificateRepository {

    List<TagGiftCertificate> findAll();

    Optional<TagGiftCertificate> findByIds(int certificateId, int tagId);

    List<TagGiftCertificate> findByTagId(int tagId);

    List<TagGiftCertificate> findByCertificateId(int certificateId);

    void save(int certificateId, int tagId);

    void delete(int certificateId, int tagId);

    boolean existsIds(int certificateId, int tagId);
}
