package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.impl.GiftCertificateMapper;
import com.epam.esm.repository.CertificatePurchaseRepository;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.esm.util.ParameterName.*;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository repository;
    private final CertificatePurchaseRepository purchaseRepository;
    private final GiftCertificateValidator validator;
    private final GiftCertificateMapper mapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository repository,
                                      CertificatePurchaseRepository purchaseRepository,
                                      GiftCertificateValidator validator,
                                      @Qualifier("certificateMapper") GiftCertificateMapper mapper) {
        this.repository = repository;
        this.purchaseRepository = purchaseRepository;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public boolean create(Map<String, Object> certificateData) {
        if (validator.checkCertificate(certificateData)) {
            Set<Tag> tags = ((ArrayList<Map<String, String>>) certificateData.get(TAGS)).stream()
                    .map(t -> t.get(NAME))
                    .map(Tag::new)
                    .collect(Collectors.toSet());
            GiftCertificate giftCertificate = new GiftCertificate.GiftCertificateBuilder()
                    .setName(String.valueOf(certificateData.get(NAME)))
                    .setDescription(String.valueOf(certificateData.get(DESCRIPTION)))
                    .setPrice(new BigDecimal(String.valueOf(certificateData.get(PRICE))))
                    .setDuration(Integer.parseInt(String.valueOf(certificateData.get(DURATION))))
                    .setTags(tags)
                    .build();
            return purchaseRepository.create(giftCertificate);
        } else {
            return false;
        }
    }

    @Override
    public boolean update(Map<String, Object> certificateData) {
        if (validator.checkCertificate(certificateData)) {
            Set<Tag> tags = ((ArrayList<Map<String, String>>) certificateData.get(TAGS)).stream()
                    .map(t -> t.get(NAME))
                    .map(Tag::new)
                    .collect(Collectors.toSet());
            GiftCertificate giftCertificate = new GiftCertificate.GiftCertificateBuilder()
                    .setId(Long.parseLong(String.valueOf(certificateData.get(ID))))
                    .setName(String.valueOf(certificateData.get(NAME)))
                    .setDescription(String.valueOf(certificateData.get(DESCRIPTION)))
                    .setPrice(new BigDecimal(String.valueOf(certificateData.get(PRICE))))
                    .setDuration(Integer.parseInt(String.valueOf(certificateData.get(DURATION))))
                    .setTags(tags)
                    .build();
            return purchaseRepository.update(giftCertificate);
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(long id) {
        return repository.delete(id);
    }

    @Override
    public List<GiftCertificateDto> findAll() {
        List<GiftCertificate> certificates = repository.findAll();
        return certificates.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<GiftCertificateDto> findById(long id) {
        Optional<GiftCertificate> certificate = repository.findById(id);
        if (certificate.isPresent()) {
            GiftCertificateDto giftCertificate = mapper.mapToDto(certificate.get());
            return Optional.of(giftCertificate);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<GiftCertificateDto> findByName(String name) {
        List<GiftCertificate> certificates = repository.findByName(name);
        return certificates.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDto> findByDescription(String description) {
        List<GiftCertificate> certificates = repository.findByDescription(description);
        return certificates.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
}
