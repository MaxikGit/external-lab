package com.epam.esm.controllers;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.services.CertificateService;
import com.epam.esm.validation.OnCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
@Validated
public class CertificateController {

    private final CertificateService certificateService;

    @GetMapping
    public List<GiftCertificateDto> findCertificate(
            @RequestParam(value = "search", defaultValue = "") String key,
            @RequestParam(value = "tag", defaultValue = "") String tag,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
            @RequestParam(value = "sortField", defaultValue = "id") String sortField) {
        return certificateService.find(tag, key, sortField, sortDirection);
    }

    @GetMapping("/{id}")
    public GiftCertificateDto getCertificate(@PathVariable("id") Integer id) {
        return certificateService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnCreate.class)
    public GiftCertificateDto createNew(@Valid @RequestBody GiftCertificateDto certificateDto) {
        return certificateService.save(certificateDto);
    }

    @PutMapping("/{id}")
    @Validated(OnCreate.class)
    public void update(@PathVariable("id") int id,
                       @Valid @RequestBody GiftCertificateDto certificateDto) {
        certificateService.update(id, certificateDto);
    }

    @PatchMapping("/{id}")
    public GiftCertificateDto partialUpdate(@PathVariable("id") int id,
                                            @Valid @RequestBody GiftCertificateDto certificateDto) {
            return certificateService.partialUpdate(id, certificateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        certificateService.delete(id);
    }
}
