package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public void create(@RequestBody Map<String, Object> tagData) {
        boolean isCreated = tagService.create(tagData);
        if (!isCreated) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable long id) {
        boolean isDeleted = tagService.delete(id);
        if (!isDeleted) {
            throw new NotFoundException();
        }
    }

    @GetMapping
    @ResponseStatus(FOUND)
    public List<TagDto> retrieveAll() {
        return tagService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(FOUND)
    public TagDto findById(@PathVariable long id) {
        Optional<TagDto> tag = tagService.findById(id);
        if (tag.isPresent()) {
            return tag.get();
        } else {
            throw new NotFoundException();
        }
    }

    @GetMapping(params = "name")
    @ResponseStatus(FOUND)
    public TagDto findByName(@RequestParam String name) {
        Optional<TagDto> tag = tagService.findByName(name);
        if (tag.isPresent()) {
            return tag.get();
        } else {
            throw new NotFoundException();
        }
    }
}
