package com.epam.esm.certificate.controller;

import com.epam.esm.certificate.dto.CertificatePriceUpdateDto;
import com.epam.esm.certificate.dto.GiftCertificateDto;
import com.epam.esm.certificate.representation.CertificateAssembler;
import com.epam.esm.certificate.service.CertificateService;
import com.epam.esm.common.validation.OnCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.epam.esm.common.controller.DefaultRequestParams.*;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
@Validated
public class CertificateController {

    private final CertificateService certificateService;
    private final CertificateAssembler assembler;

    @GetMapping
    public PagedModel<EntityModel<GiftCertificateDto>> findCertificates(
            @RequestParam(value = "search", defaultValue = SEARCH_KEY) String key,
            @RequestParam(value = "tags", defaultValue = SEARCH_KEY) List<String> tags,
            @RequestParam(value = "sortDirection", defaultValue = SORT_DIRECTION) String sortDirection,
            @RequestParam(value = "sortField", defaultValue = SORT_FIELD) String sortField,
            @RequestParam(value = "offset", defaultValue = OFFSET) int offset,
            @RequestParam(value = "size", defaultValue = SIZE) int size) {
        PagedModel<GiftCertificateDto> models = certificateService
                .find(key, tags, sortField, sortDirection, offset, size);
        return assembler.toPagedModel(models, key, tags, sortField, sortDirection, offset, size);
    }

    @GetMapping("/{id}")
    public EntityModel<GiftCertificateDto> getCertificate(@PathVariable("id") Integer id) {
        GiftCertificateDto dto = certificateService.findById(id);
        return assembler.toEntityModel(dto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnCreate.class)
    public EntityModel<GiftCertificateDto> createNew(@Valid @RequestBody GiftCertificateDto certificateDto) {
        GiftCertificateDto dto = certificateService.save(certificateDto);
        return assembler.toEntityModel(dto);
    }

    @PutMapping("/{id}")
    @Validated(OnCreate.class)
    public EntityModel<GiftCertificateDto> update(@PathVariable("id") int id,
                                                  @Valid @RequestBody GiftCertificateDto certificateDto) {
        GiftCertificateDto dto = certificateService.update(id, certificateDto);
        return assembler.toEntityModel(dto);
    }

    @PatchMapping("/{id}")
    public EntityModel<GiftCertificateDto> partialUpdate(@PathVariable("id") int id,
                                                         @Valid @RequestBody GiftCertificateDto certificateDto) {
        GiftCertificateDto dto =  certificateService.partialUpdate(id, certificateDto);
        return assembler.toEntityModel(dto);
    }

    @PatchMapping("/price")
    public EntityModel<GiftCertificateDto> priceUpdate(@Valid @RequestBody CertificatePriceUpdateDto certificatePriceUpdateDto) {
        GiftCertificateDto dto =  certificateService.priceUpdate(certificatePriceUpdateDto);
        return assembler.toEntityModel(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        certificateService.delete(id);
    }
}
