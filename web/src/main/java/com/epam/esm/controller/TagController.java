package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.NoDataFoundException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.util.ParameterName.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * The type Tag controller.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@RestController
@Validated
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    /**
     * Instantiates a new Tag controller.
     *
     * @param tagService the tag service
     */
    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Create.
     *
     * @param tagDto the tag dto
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public void create(@RequestBody TagDto tagDto) {
        boolean isCreated = tagService.create(tagDto);
        if (!isCreated) {
            throw new BadRequestException(TagDto.class);
        }
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable @Min(1) long id) {
        boolean isDeleted = tagService.delete(id);
        if (!isDeleted) {
            throw new NoDataFoundException(ID, id, TagDto.class);
        }
    }

    /**
     * Retrieve all set.
     *
     * @param page the page
     * @return the set
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public Set<TagDto> retrieveAll(@RequestParam @Min(1) int page) {
        Set<TagDto> tags = tagService.findAll(page);
        if (!tags.isEmpty()) {
            return tags;
        } else {
            throw new NoDataFoundException(TAGS, TagDto.class);
        }
    }

    /**
     * Find by id tag dto.
     *
     * @param id the id
     * @return the tag dto0
     */
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public TagDto findById(@PathVariable @Min(1) long id) {
        Optional<TagDto> tag = tagService.findById(id);
        if (tag.isPresent()) {
            return tag.get();
        } else {
            throw new NoDataFoundException(ID, id, TagDto.class);
        }
    }

    /**
     * Find by name tag dto.
     *
     * @param name the name
     * @return the tag dto
     */
    @GetMapping(params = "name", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public TagDto findByName(@RequestParam
                             @NotNull
                             @Pattern(regexp = "[а-я\\p{Lower} _]{2,50}")
                                     String name) {
        Optional<TagDto> tag = tagService.findByName(name);
        if (tag.isPresent()) {
            return tag.get();
        } else {
            throw new NoDataFoundException(NAME, name, TagDto.class);
        }
    }

    /**
     * Find most used tag set.
     *
     * @param page the page
     * @return the set
     */
    @GetMapping(value = "/most-used-tag", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public Set<TagDto> findMostUsedTag(@RequestParam @Min(1) int page) {
        Set<TagDto> orders = tagService.findMostUsedTag(page);
        if (!orders.isEmpty()) {
            return orders;
        } else {
            throw new NoDataFoundException(MOST_USED_TAG, OrderDto.class);
        }
    }
}
