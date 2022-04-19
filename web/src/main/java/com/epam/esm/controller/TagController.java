package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.NoDataFoundException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.util.ParameterName.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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
public class TagController extends AbstractController<TagDto> {
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
    public void create(@RequestBody @Valid TagDto tagDto) {
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
    public CollectionModel<TagDto> retrieveAll(@RequestParam @Min(1) int page) {
        Set<TagDto> tags = tagService.findAll(page);
        int lastPage = tagService.getLastPage();
        if (!tags.isEmpty()) {
            addLinksToTags(tags);
            CollectionModel<TagDto> method = methodOn(TagController.class).retrieveAll(page);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(tags, links);
        } else {
            throw new NoDataFoundException(TAGS, TagDto.class);
        }
    }

    /**
     * Retrieve by id tag dto.
     *
     * @param id the id
     * @return the tag dto0
     */
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public TagDto retrieveById(@PathVariable @Min(value = 1) long id) {
        Optional<TagDto> tag = tagService.findById(id);
        if (tag.isPresent()) {
            Link link = linkTo(methodOn(TagController.class).retrieveById(id)).withSelfRel();
            tag.get().add(link);
            return tag.get();
        } else {
            throw new NoDataFoundException(ID, id, TagDto.class);
        }
    }

    /**
     * Retrieve by name tag dto.
     *
     * @param name the name
     * @return the tag dto
     */
    @GetMapping(params = "name", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public TagDto retrieveByName(@RequestParam
                                 @NotNull
                                 @Pattern(regexp = "[а-я\\p{Lower}_]{1,50}")
                                         String name) {
        Optional<TagDto> tag = tagService.findByName(name);
        if (tag.isPresent()) {
            Link link = linkTo(methodOn(TagController.class).retrieveByName(name)).withSelfRel();
            tag.get().add(link);
            return tag.get();
        } else {
            throw new NoDataFoundException(NAME, name, TagDto.class);
        }
    }

    /**
     * Retrieve most used tag set.
     *
     * @param page the page
     * @return the set
     */
    @GetMapping(value = "/most-used-tag", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public CollectionModel<TagDto> retrieveMostUsedTag(@RequestParam @Min(1) int page) {
        Set<TagDto> tags = tagService.findMostUsedTag(page);
        int lastPage = tagService.getLastPage();
        if (!tags.isEmpty()) {
            addLinksToTags(tags);
            CollectionModel<TagDto> method = methodOn(TagController.class).retrieveMostUsedTag(page);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(tags, links);
        } else {
            throw new NoDataFoundException(MOST_USED_TAG, OrderDto.class);
        }
    }

    protected void addLinksToTags(Set<TagDto> tags) {
        tags.forEach(t -> {
            Link selfLink = linkTo(methodOn(TagController.class).retrieveById(t.getId())).withSelfRel();
            t.add(selfLink);
        });
    }
}