package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/certificates")
public class CertificateController {
    private final GiftCertificateService certificateService;

    @Autowired
    public CertificateController(GiftCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public void create(@RequestBody Map<String, String> certificateData) {
        boolean isCreated = certificateService.create(certificateData);
        if (!isCreated) {
            throw new BadRequestException();
        }
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public void update(@RequestBody Map<String, String> certificateData) {
        boolean isUpdated = certificateService.update(certificateData);
        if (!isUpdated) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable long id) {
        boolean isDeleted = certificateService.delete(id);
        if (!isDeleted) {
            throw new NotFoundException();
        }
    }

    @GetMapping
    @ResponseStatus(FOUND)
    public List<GiftCertificateDto> retrieveAll() {
        return certificateService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(FOUND)
    public GiftCertificateDto findById(@PathVariable long id) {
        Optional<GiftCertificateDto> giftCertificate = certificateService.findById(id);
        if (giftCertificate.isPresent()) {
            return giftCertificate.get();
        } else {
            throw new NotFoundException();
        }
    }

    @GetMapping
    @ResponseStatus(FOUND)
    public List<GiftCertificateDto> findByName(@RequestParam String name) {
        return certificateService.findByName(name);
    }

    @GetMapping
    @ResponseStatus(FOUND)
    public List<GiftCertificateDto> findByDescription(@RequestParam String description) {
        return certificateService.findByDescription(description);
    }
}
