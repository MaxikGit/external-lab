package com.epam.esm.services;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exceptions.CertificateNotFoundException;
import com.epam.esm.mappers.CertificateDtoMapper;
import com.epam.esm.models.GiftCertificate;
import com.epam.esm.repositories.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private static final CertificateDtoMapper CERTIFICATE_DTO_MAPPER = CertificateDtoMapper.INSTANCE;

    @Override
    public List<GiftCertificateDto> findAll(String sortField, String sortDirection) {
        return CERTIFICATE_DTO_MAPPER.toDto(certificateRepository.findAll(sortField, sortDirection));
    }

    @Override
    public GiftCertificateDto findById(int id) {
        return CERTIFICATE_DTO_MAPPER.toDto(certificateRepository
                .findById(id)
                .orElseThrow(() -> new CertificateNotFoundException(id)));
    }

    @Override
    public List<GiftCertificateDto> find(String tag, String key, String sortField, String sortDirection) {
        if (key.isEmpty() && tag.isEmpty()) {
            return findAll(sortField, sortDirection);
        } else if (tag.isEmpty())
            return CERTIFICATE_DTO_MAPPER.toDto(certificateRepository
                    .searchByName(key, sortField, sortDirection));
        return CERTIFICATE_DTO_MAPPER.toDto(certificateRepository
                .searchByTagAndName(tag, key, sortField, sortDirection));
    }

    @Override
    public GiftCertificateDto save(GiftCertificateDto certificateDto) {
        GiftCertificate certificate = CERTIFICATE_DTO_MAPPER.fromDto(certificateDto);
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        int id = certificateRepository.save(certificate);
        certificate.setId(id);
        return CERTIFICATE_DTO_MAPPER.toDto(certificate);
    }

    @Override
    public void update(int id, GiftCertificateDto certificateDto) {
        checkIfExist(id);
        GiftCertificate certificate = CERTIFICATE_DTO_MAPPER.fromDto(certificateDto);
        certificate.setLastUpdateDate(LocalDateTime.now());
        certificateRepository.update(id, certificate);
    }

    @Override
    public GiftCertificateDto partialUpdate(int id, GiftCertificateDto certificateDto) {
        GiftCertificate oldCertificate = certificateRepository
                .findById(id)
                .orElseThrow(() -> new CertificateNotFoundException(id));
        GiftCertificate newCertificate = CERTIFICATE_DTO_MAPPER.copyNotNullFields(oldCertificate, certificateDto);
        newCertificate.setLastUpdateDate(LocalDateTime.now());
        certificateRepository.update(id, newCertificate);
        return CERTIFICATE_DTO_MAPPER.toDto(newCertificate);
    }

    @Override
    public void delete(int id) {
        checkIfExist(id);
        certificateRepository.delete(id);
    }

    private void checkIfExist(int id) {
        if (!certificateRepository.existsById(id))
            throw new CertificateNotFoundException(id);
    }
}
