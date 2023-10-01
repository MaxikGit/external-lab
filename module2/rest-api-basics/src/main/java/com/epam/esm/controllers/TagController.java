package com.epam.esm.controllers;

import com.epam.esm.dto.TagDto;
import com.epam.esm.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public List<TagDto> searchTagByName(@RequestParam(value = "search", defaultValue = "") String key,
                                        @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
                                        @RequestParam(value = "sortField", defaultValue = "id") String sortField) {
        return tagService.find(key, sortField, sortDirection);
    }

    @GetMapping("/{Id}")
    public TagDto getTag(@Valid @PathVariable("Id") int id) {
        return tagService.findById(id);
    }

    @GetMapping("/name/{name}")
    public TagDto getTag(@Valid @PathVariable("name") String name) {
        return tagService.findByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto createNew(@Valid @RequestBody TagDto tagDto) {
        return tagService.save(tagDto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") int id, @Valid @RequestBody TagDto tagDto) {
        tagService.update(id, tagDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        tagService.delete(id);
    }
}
