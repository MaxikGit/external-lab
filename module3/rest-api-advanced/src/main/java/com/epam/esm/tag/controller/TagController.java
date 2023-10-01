package com.epam.esm.tag.controller;

import com.epam.esm.tag.dto.TagDto;
import com.epam.esm.tag.representation.TagAssembler;
import com.epam.esm.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.epam.esm.common.controller.DefaultRequestParams.*;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagAssembler assembler;

    @GetMapping
    public PagedModel<EntityModel<TagDto>> findTagsWithNameStarting(
            @RequestParam(value = "search", defaultValue = SEARCH_KEY) String key,
            @RequestParam(value = "sortField", defaultValue = SORT_FIELD) String sortField,
            @RequestParam(value = "sortDirection", defaultValue = SORT_DIRECTION) String sortDirection,
            @RequestParam(value = "offset", defaultValue = OFFSET) int offset,
            @RequestParam(value = "size", defaultValue = SIZE) int size
    ) {
        PagedModel<TagDto> models = tagService.find(key, sortField, sortDirection, offset, size);
        return assembler.toPagedModel(models, key, sortField, sortDirection, offset, size);
    }

    @GetMapping("/{id}")
    public EntityModel<TagDto> getTag(@PathVariable("id") int id) {
        TagDto dto = tagService.findById(id);
        return assembler.toEntityModel(dto);
    }

    @GetMapping("/name/{name}")
    public EntityModel<TagDto> getTag(@PathVariable("name") String name) {
        TagDto dto = tagService.findByName(name);
        return assembler.toEntityModel(dto);
    }

    @GetMapping("/most_popular_tag")
    public EntityModel<TagDto> getMostPopularTag(@RequestParam("userId") int userId) {
        TagDto dto = tagService.mostPopularTag(userId);
        return assembler.toEntityModel(dto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<TagDto> createNew(@Valid @RequestBody TagDto tagDto) {
        TagDto dto = tagService.save(tagDto);
        return assembler.toEntityModel(dto);
    }

    @PutMapping("/{id}")
    public EntityModel<TagDto> update(@PathVariable("id") int id, @Valid @RequestBody TagDto tagDto) {
        TagDto dto = tagService.update(id, tagDto);
        return assembler.toEntityModel(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        tagService.delete(id);
    }
}
