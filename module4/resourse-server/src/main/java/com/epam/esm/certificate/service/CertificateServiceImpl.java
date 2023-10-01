package com.epam.esm.certificate.service;

import com.epam.esm.certificate.dto.CertificatePriceUpdateDto;
import com.epam.esm.certificate.dto.GiftCertificateDto;
import com.epam.esm.certificate.mapper.CertificateDtoMapper;
import com.epam.esm.certificate.model.GiftCertificateModel;
import com.epam.esm.certificate.repository.CertificateRepository;
import com.epam.esm.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;

    @Override
    public Page<GiftCertificateDto> findAll(Pageable pageRequest) {
        Page<GiftCertificateModel> models = certificateRepository.findAll(pageRequest);
        return CertificateDtoMapper.INSTANCE.toDto(models);
    }

    @Override
    public GiftCertificateDto findById(int id) {
        return certificateRepository.findById(id)
                .map(CertificateDtoMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public Page<GiftCertificateDto> find(String key, List<String> tagNames, Pageable pageRequest) {
        if (key.isEmpty() && tagNames.isEmpty()) {
            return findAll(pageRequest);
        } else if (tagNames.isEmpty()) {
            Page<GiftCertificateModel> models = certificateRepository.findByNameStartingWith(key, pageRequest);
            return CertificateDtoMapper.INSTANCE.toDto(models);
        }
        Page<GiftCertificateModel> models = certificateRepository.findByTagsAndName(key, tagNames, pageRequest);
        return CertificateDtoMapper.INSTANCE.toDto(models);
    }

    @Override
    public GiftCertificateDto save(GiftCertificateDto certificateDto) {
        certificateDto.setId(null);
        GiftCertificateModel certificate = CertificateDtoMapper.INSTANCE.toModel(certificateDto);
        certificateRepository.save(certificate);
        return CertificateDtoMapper.INSTANCE.toDto(certificate);
    }

    @Override
    public GiftCertificateDto update(int id, GiftCertificateDto certificateDto) {
        checkIfExist(id);
        certificateDto.setId(id);
        GiftCertificateModel certificate = CertificateDtoMapper.INSTANCE.toModel(certificateDto);
        certificateRepository.save(certificate);
        return CertificateDtoMapper.INSTANCE.toDto(certificate);
    }

    @Override
    public GiftCertificateDto partialUpdate(int id, GiftCertificateDto certificateDto) {
        GiftCertificateModel oldCertificate = certificateRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        certificateDto.setId(id);
        GiftCertificateModel newCertificate = CertificateDtoMapper
                .INSTANCE.copyNotNullFields(oldCertificate, certificateDto);
        certificateRepository.save(newCertificate);
        return CertificateDtoMapper.INSTANCE.toDto(newCertificate);
    }

    @Override
    public GiftCertificateDto priceUpdate(CertificatePriceUpdateDto certificatePriceUpdateDto) {
        GiftCertificateModel certificate = certificateRepository
                .findById(certificatePriceUpdateDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(certificatePriceUpdateDto.getId()));
        certificate.setPrice(certificatePriceUpdateDto.getPrice());
        certificateRepository.save(certificate);
        return CertificateDtoMapper.INSTANCE.toDto(certificate);
    }

    @Override
    public void delete(int id) {
        GiftCertificateModel certificate = certificateRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        certificateRepository.delete(certificate);
    }

    private void checkIfExist(int id) {
        if (!certificateRepository.existsById(id))
            throw new ResourceNotFoundException(id);
    }
}
