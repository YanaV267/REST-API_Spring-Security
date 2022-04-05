package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.impl.GiftCertificateMapper;
import com.epam.esm.repository.CertificatePurchaseRepository;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.CertificateDateFormatter;
import com.epam.esm.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.esm.util.ParameterName.*;

/**
 * The type Gift certificate service.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository repository;
    private final CertificatePurchaseRepository purchaseRepository;
    private final GiftCertificateValidator validator;
    private final GiftCertificateMapper mapper;
    private final CertificateDateFormatter dateFormatter;

    /**
     * Instantiates a new Gift certificate service.
     *
     * @param repository         the repository
     * @param purchaseRepository the purchase repository
     * @param validator          the validator
     * @param mapper             the mapper
     * @param dateFormatter      the date formatter
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository repository,
                                      CertificatePurchaseRepository purchaseRepository,
                                      GiftCertificateValidator validator,
                                      @Qualifier("certificateMapper") GiftCertificateMapper mapper,
                                      CertificateDateFormatter dateFormatter) {
        this.repository = repository;
        this.purchaseRepository = purchaseRepository;
        this.validator = validator;
        this.mapper = mapper;
        this.dateFormatter = dateFormatter;
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
        if (!certificateData.isEmpty() && certificateData.containsKey(ID)
                && validator.checkCertificate(certificateData)) {
            GiftCertificate giftCertificate = retrieveCertificateData(certificateData);
            giftCertificate.setId(Long.parseLong(String.valueOf(certificateData.get(ID))));
            if (certificateData.containsKey(TAGS)) {
                Set<Tag> tags = ((ArrayList<Map<String, String>>) certificateData.get(TAGS)).stream()
                        .map(t -> t.get(NAME))
                        .map(Tag::new)
                        .collect(Collectors.toSet());
                giftCertificate.setTags(tags);
            }
            return purchaseRepository.update(giftCertificate);
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(long id) {
        if (repository.findById(id).isPresent()) {
            repository.delete(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Set<GiftCertificateDto> findAll() {
        Set<GiftCertificate> certificates = repository.findAll();
        return certificates.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toSet());
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
    public Set<GiftCertificateDto> findBySeveralParameters(Map<String, Object> certificateData, List<String> sortTypes) {
        if (!certificateData.isEmpty() && validator.checkCertificate(certificateData)) {
            GiftCertificate giftCertificate = retrieveCertificateData(certificateData);
            String tagName = null;
            if (certificateData.containsKey(TAG)) {
                tagName = String.valueOf(certificateData.get(TAG));
            }
            Set<GiftCertificate> certificates = repository.findBySeveralParameters(giftCertificate, tagName, sortTypes);
            return certificates.stream()
                    .map(mapper::mapToDto)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
        return new LinkedHashSet<>();
    }

    /**
     * Retrieve certificate data gift certificate.
     *
     * @param certificateData the certificate data
     * @return the gift certificate
     */
    public GiftCertificate retrieveCertificateData(Map<String, Object> certificateData) {
        GiftCertificate giftCertificate = new GiftCertificate();
        if (certificateData.containsKey(NAME)) {
            giftCertificate.setName(String.valueOf(certificateData.get(NAME)));
        }
        if (certificateData.containsKey(DESCRIPTION)) {
            giftCertificate.setDescription(String.valueOf(certificateData.get(DESCRIPTION)));
        }
        if (certificateData.containsKey(PRICE)) {
            giftCertificate.setPrice(new BigDecimal(String.valueOf(certificateData.get(PRICE))));
        }
        if (certificateData.containsKey(DURATION)) {
            giftCertificate.setDuration(Integer.parseInt(String.valueOf(certificateData.get(DURATION))));
        }
        if (certificateData.containsKey(CREATE_DATE)) {
            LocalDateTime formattedDate = dateFormatter.format(String.valueOf(certificateData.get(CREATE_DATE)));
            giftCertificate.setCreateDate(formattedDate);
        }
        if (certificateData.containsKey(LAST_UPDATE_DATE)) {
            LocalDateTime formattedDate = dateFormatter.format(String.valueOf(certificateData.get(LAST_UPDATE_DATE)));
            giftCertificate.setLastUpdateDate(formattedDate);
        }
        return giftCertificate;
    }
}
