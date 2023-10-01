package com.epam.esm.tag.representation;

import com.epam.esm.certificate.controller.CertificateController;
import com.epam.esm.common.util.PaginationHelper;
import com.epam.esm.tag.controller.TagController;
import com.epam.esm.tag.dto.TagDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.epam.esm.common.controller.DefaultRequestParams.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagAssembler {

    public EntityModel<TagDto> toEntityModel(TagDto dto) {
        return EntityModel.of(dto).add(
                linkTo(methodOn(TagController.class).getTag(dto.getId())).withSelfRel(),
                linkTo(methodOn(TagController.class).getTag(dto.getName())).withSelfRel(),
                linkTo(methodOn(CertificateController.class).findCertificates(
                        SEARCH_KEY, List.of(dto.getName()), SORT_FIELD, SORT_DIRECTION,
                        Integer.parseInt(OFFSET), Integer.parseInt(SIZE))).withRel("certificates_with_tag")
        );
    }

    public PagedModel<EntityModel<TagDto>> toPagedModel(PagedModel<TagDto> models, String key, String sortField, String sortDirection, int offset, int size) {
        PagedModel<EntityModel<TagDto>> result = PagedModel.of(
                models.getContent().stream().map(this::toEntityModel).toList(),
                models.getMetadata());
        Link link = linkTo(methodOn(TagController.class)
                .findTagsWithNameStarting(key, sortField, sortDirection, offset, size)).withSelfRel();
        PaginationHelper.insertPageLinks(result, link);
        return result;
    }
}
