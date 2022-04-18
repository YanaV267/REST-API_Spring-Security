package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.NoDataFoundException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.util.ParameterName.CERTIFICATES;
import static com.epam.esm.util.ParameterName.ID;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * The type Gift certificate controller.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
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
     * @param certificateData the certificate data
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public void create(@RequestBody Map<String, Object> certificateData) {
        boolean isCreated = certificateService.create(certificateData);
        if (!isCreated) {
            throw new BadRequestException(GiftCertificateDto.class);
        }
    }

    /**
     * Update.
     *
     * @param certificateData the certificate data
     */
    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public void update(@RequestBody Map<String, Object> certificateData) {
        boolean isUpdated = certificateService.update(certificateData);
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
    public void delete(@PathVariable long id) {
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
    public Set<GiftCertificateDto> retrieveAll(@RequestParam int page) {
        Set<GiftCertificateDto> certificates = certificateService.findAll(page);
        if (!certificates.isEmpty()) {
            return certificates;
        } else {
            throw new NoDataFoundException(CERTIFICATES, GiftCertificateDto.class);
        }
    }

    /**
     * Find by id gift certificate dto.
     *
     * @param id the id
     * @return the gift certificate dto
     */
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public GiftCertificateDto findById(@PathVariable long id) {
        Optional<GiftCertificateDto> giftCertificate = certificateService.findById(id);
        if (giftCertificate.isPresent()) {
            return giftCertificate.get();
        } else {
            throw new NoDataFoundException(ID, id, GiftCertificateDto.class);
        }
    }

    /**
     * Find by several parameters set.
     *
     * @param page            the page
     * @param certificateData the certificate data
     * @param tagNames        the tagNames
     * @param sortTypes       the sort types
     * @return the set
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public Set<GiftCertificateDto> findBySeveralParameters(@RequestParam(value = "page") int page,
                                                           @RequestParam Map<String, Object> certificateData,
                                                           @RequestParam(value = "tag", required = false) List<String> tagNames,
                                                           @RequestParam(value = "sort", required = false) List<String> sortTypes) {
        Set<GiftCertificateDto> certificates = certificateService.findBySeveralParameters(page,
                certificateData, tagNames, sortTypes);
        if (!certificates.isEmpty()) {
            return certificates;
        } else {
            throw new NoDataFoundException(certificateData.toString(), GiftCertificateDto.class);
        }
    }
}
