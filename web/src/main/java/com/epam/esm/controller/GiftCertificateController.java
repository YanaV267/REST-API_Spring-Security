package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.NoDataFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validation.OnCreateGroup;
import com.epam.esm.validation.OnSearchGroup;
import com.epam.esm.validation.OnUpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.util.ParameterName.CERTIFICATES;
import static com.epam.esm.util.ParameterName.ID;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * The type Gift certificate controller.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@RestController
@Validated
@RequestMapping("/certificates")
public class GiftCertificateController extends AbstractController<GiftCertificateDto> {
    private final GiftCertificateService certificateService;

    /**
     * Instantiates a new Gift certificate controller.
     *
     * @param certificateService the certificate service
     */
    @Autowired
    public GiftCertificateController(GiftCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * Create.
     *
     * @param certificateDto the certificate dto
     */
    @Validated(OnCreateGroup.class)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    @Secured("ROLE_ADMIN")
    public void create(@RequestBody @Valid GiftCertificateDto certificateDto) {
        boolean isCreated = certificateService.create(certificateDto);
        if (!isCreated) {
            throw new BadRequestException(GiftCertificateDto.class);
        }
    }

    /**
     * Update.
     *
     * @param certificateDto the certificate dto
     */
    @Validated(OnUpdateGroup.class)
    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    @Secured("ROLE_ADMIN")
    public void update(@RequestBody @Valid GiftCertificateDto certificateDto) {
        boolean isUpdated = certificateService.update(certificateDto);
        if (!isUpdated) {
            throw new BadRequestException(GiftCertificateDto.class);
        }
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    @Secured("ROLE_ADMIN")
    public void delete(@PathVariable @Min(1) long id) {
        boolean isDeleted = certificateService.delete(id);
        if (!isDeleted) {
            throw new NoDataFoundException(ID, id, GiftCertificateDto.class);
        }
    }

    /**
     * Retrieve all set.
     *
     * @param page the page
     * @return the set
     */
    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public CollectionModel<GiftCertificateDto> retrieveAll(@RequestParam @Min(1) int page) {
        Set<GiftCertificateDto> certificates = certificateService.findAll(page);
        int lastPage = certificateService.getLastPage();
        if (!certificates.isEmpty()) {
            addLinksToCertificates(certificates);
            CollectionModel<GiftCertificateDto> method = methodOn(GiftCertificateController.class).retrieveAll(page);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(certificates, links);
        } else {
            throw new NoDataFoundException(CERTIFICATES, GiftCertificateDto.class);
        }
    }

    /**
     * Retrieve by id gift certificate dto.
     *
     * @param id the id
     * @return the gift certificate dto
     */
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public GiftCertificateDto retrieveById(@PathVariable @Min(1) long id) {
        Optional<GiftCertificateDto> giftCertificate = certificateService.findById(id);
        if (giftCertificate.isPresent()) {
            Link link = linkTo(methodOn(GiftCertificateController.class).retrieveById(id)).withSelfRel();
            giftCertificate.get().add(link);
            return giftCertificate.get();
        } else {
            throw new NoDataFoundException(ID, id, GiftCertificateDto.class);
        }
    }

    /**
     * Retrieve by several parameters set.
     *
     * @param page           the page
     * @param certificateDto the certificate dto
     * @param tagNames       the tag names
     * @param sortTypes      the sort types
     * @return the set
     */
    @Validated(OnSearchGroup.class)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public CollectionModel<GiftCertificateDto> retrieveBySeveralParameters(
            @RequestParam(value = "page") @Min(1) int page,
            @Valid GiftCertificateDto certificateDto,
            @RequestParam(value = "tag", required = false)
                    List<@Pattern(regexp = "[а-я\\p{Lower} _]{2,50}")
                            String> tagNames,
            @RequestParam(value = "sort", required = false)
                    List<@Pattern(regexp = "[a-z]_(de)|(a)sc") String> sortTypes) {
        Set<GiftCertificateDto> certificates = certificateService.findBySeveralParameters(
                page, certificateDto, tagNames, sortTypes);
        int lastPage = certificateService.getLastPage();
        if (!certificates.isEmpty()) {
            addLinksToCertificates(certificates);
            CollectionModel<GiftCertificateDto> method = methodOn(GiftCertificateController.class)
                    .retrieveBySeveralParameters(page, certificateDto, tagNames, sortTypes);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(certificates, links);
        } else {
            throw new NoDataFoundException(certificateDto.toString(), GiftCertificateDto.class);
        }
    }

    private void addLinksToCertificates(Set<GiftCertificateDto> certificates) {
        certificates.forEach(c -> {
            Link selfLink = linkTo(methodOn(GiftCertificateController.class).retrieveById(c.getId())).withSelfRel();
            c.add(selfLink);
        });
    }
}
