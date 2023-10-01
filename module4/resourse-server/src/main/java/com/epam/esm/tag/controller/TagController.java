package com.epam.esm.tag.controller;

import com.epam.esm.tag.dto.TagDto;
import com.epam.esm.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.epam.esm.common.controller.DefaultRequestParams.SEARCH_KEY;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('USER')")
public class TagController {

    private final TagService tagService;
    private final RepresentationModelAssembler<TagDto, EntityModel<TagDto>> tagLinkAssembler;
    private final PagedResourcesAssembler<TagDto> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<EntityModel<TagDto>> findTagsWithNameStarting(
            @RequestParam(value = "name", defaultValue = SEARCH_KEY) String name,
            Pageable pageRequest) {
        Page<TagDto> models = tagService.find(name, pageRequest);
        return pagedResourcesAssembler.toModel(models, tagLinkAssembler);
    }

    @GetMapping("/{id}")
    public EntityModel<TagDto> getTag(@PathVariable("id") int id) {
        TagDto dto = tagService.findById(id);
        return tagLinkAssembler.toModel(dto);
    }

    @GetMapping("/name/{name}")
    public EntityModel<TagDto> getTag(@PathVariable("name") String name) {
        TagDto dto = tagService.findByName(name);
        return tagLinkAssembler.toModel(dto);
    }

    @GetMapping("/most_popular_tag")
    public EntityModel<TagDto> getMostPopularTag(@RequestParam("userId") int userId) {
        TagDto dto = tagService.mostPopularTag(userId);
        return tagLinkAssembler.toModel(dto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<TagDto> createNew(@Valid @RequestBody TagDto tagDto) {
        TagDto dto = tagService.save(tagDto);
        return tagLinkAssembler.toModel(dto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public EntityModel<TagDto> update(@PathVariable("id") int id, @Valid @RequestBody TagDto tagDto) {
        TagDto dto = tagService.update(id, tagDto);
        return tagLinkAssembler.toModel(dto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        tagService.delete(id);
    }
}
