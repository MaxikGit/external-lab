package com.epam.esm.certificate.controller;

import com.epam.esm.certificate.dto.CertificatePriceUpdateDto;
import com.epam.esm.certificate.dto.GiftCertificateDto;
import com.epam.esm.certificate.service.CertificateService;
import com.epam.esm.common.validation.OnCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.epam.esm.common.controller.DefaultRequestParams.SEARCH_KEY;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
@Validated
@PreAuthorize("hasAuthority('ADMIN')")
public class CertificateController {

    private final CertificateService certificateService;
    private final RepresentationModelAssembler<GiftCertificateDto, EntityModel<GiftCertificateDto>> certificateLinkAssembler;
    private final PagedResourcesAssembler<GiftCertificateDto> pagedResourcesAssembler;

    @PreAuthorize("permitAll()")
    @GetMapping
    public PagedModel<EntityModel<GiftCertificateDto>> findCertificates(
            @RequestParam(value = "name", defaultValue = SEARCH_KEY) String key,
            @RequestParam(value = "tags", defaultValue = SEARCH_KEY) List<String> tags,
            Pageable pageRequest
    ) {
        Page<GiftCertificateDto> models = certificateService.find(key, tags, pageRequest);
        return pagedResourcesAssembler.toModel(models, certificateLinkAssembler);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public EntityModel<GiftCertificateDto> getCertificate(@PathVariable("id") Integer id) {
        GiftCertificateDto dto = certificateService.findById(id);
        return certificateLinkAssembler.toModel(dto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnCreate.class)
    public EntityModel<GiftCertificateDto> createNew(@Valid @RequestBody GiftCertificateDto certificateDto) {
        GiftCertificateDto dto = certificateService.save(certificateDto);
        return certificateLinkAssembler.toModel(dto);
    }

    @PutMapping("/{id}")
    @Validated(OnCreate.class)
    public EntityModel<GiftCertificateDto> update(@PathVariable("id") int id,
                                                  @Valid @RequestBody GiftCertificateDto certificateDto) {
        GiftCertificateDto dto = certificateService.update(id, certificateDto);
        return certificateLinkAssembler.toModel(dto);
    }

    @PatchMapping("/{id}")
    public EntityModel<GiftCertificateDto> partialUpdate(@PathVariable("id") int id,
                                                         @Valid @RequestBody GiftCertificateDto certificateDto) {
        GiftCertificateDto dto =  certificateService.partialUpdate(id, certificateDto);
        return certificateLinkAssembler.toModel(dto);
    }

    @PatchMapping("/price")
    public EntityModel<GiftCertificateDto> priceUpdate(@Valid @RequestBody CertificatePriceUpdateDto certificatePriceUpdateDto) {
        GiftCertificateDto dto =  certificateService.priceUpdate(certificatePriceUpdateDto);
        return certificateLinkAssembler.toModel(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        certificateService.delete(id);
    }
}
