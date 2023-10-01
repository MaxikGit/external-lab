package com.epam.esm.tag.representation;

import com.epam.esm.certificate.controller.CertificateController;
import com.epam.esm.tag.controller.TagController;
import com.epam.esm.tag.dto.TagDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.epam.esm.common.controller.DefaultRequestParams.SEARCH_KEY;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagLinkAssembler implements RepresentationModelAssembler<TagDto, EntityModel<TagDto>> {

    @Override
    public EntityModel<TagDto> toModel(TagDto dto) {
        return EntityModel.of(dto).add(
                linkTo(methodOn(TagController.class).getTag(dto.getId())).withSelfRel(),
                linkTo(methodOn(TagController.class).getTag(dto.getName())).withSelfRel(),
                linkTo(methodOn(CertificateController.class).findCertificates(
                        SEARCH_KEY, List.of(dto.getName()), Pageable.ofSize(20))).withRel("certificates_with_tag")
        );
    }
}
