package com.epam.esm.certificate.representation;

import com.epam.esm.certificate.controller.CertificateController;
import com.epam.esm.certificate.dto.GiftCertificateDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CertificateLinkAssembler implements RepresentationModelAssembler<GiftCertificateDto, EntityModel<GiftCertificateDto>> {

    @Override
    public EntityModel<GiftCertificateDto> toModel(GiftCertificateDto dto) {
        return EntityModel.of(dto).add(
                linkTo(methodOn(CertificateController.class).getCertificate(dto.getId())).withSelfRel());
    }
}
