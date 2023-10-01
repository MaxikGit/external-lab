package com.epam.esm.certificate.representation;

import com.epam.esm.certificate.controller.CertificateController;
import com.epam.esm.certificate.dto.GiftCertificateDto;
import com.epam.esm.common.util.PaginationHelper;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CertificateAssembler{

    public EntityModel<GiftCertificateDto> toEntityModel(GiftCertificateDto dto) {
        return EntityModel.of(dto).add(
                linkTo(methodOn(CertificateController.class).getCertificate(dto.getId())).withSelfRel());
    }

    public PagedModel<EntityModel<GiftCertificateDto>> toPagedModel(
            PagedModel<GiftCertificateDto> models, String key, List<String> tags,
            String sortField, String sortDirection, int offset, int size
    ) {
        PagedModel<EntityModel<GiftCertificateDto>> result = PagedModel.of(
                models.getContent().stream().map(this::toEntityModel).toList(),
                models.getMetadata());
        Link link = linkTo(methodOn(CertificateController.class)
                .findCertificates(key, tags, sortField, sortDirection, offset, size)).withSelfRel();
        PaginationHelper.insertPageLinks(result, link);
        return result;
    }
}
