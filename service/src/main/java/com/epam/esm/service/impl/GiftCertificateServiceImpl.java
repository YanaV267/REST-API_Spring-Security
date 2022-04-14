package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.impl.GiftCertificateMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.CertificateDateFormatter;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private final GiftCertificateValidator validator;
    private final TagValidator tagValidator;
    private final GiftCertificateMapper mapper;
    private final CertificateDateFormatter dateFormatter;

    /**
     * Instantiates a new Gift certificate service.
     *
     * @param repository    the repository
     * @param validator     the validator
     * @param tagValidator  the tag validator
     * @param mapper        the mapper
     * @param dateFormatter the date formatter
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository repository,
                                      GiftCertificateValidator validator,
                                      TagValidator tagValidator,
                                      @Qualifier("certificateServiceMapper") GiftCertificateMapper mapper,
                                      CertificateDateFormatter dateFormatter) {
        this.repository = repository;
        this.validator = validator;
        this.tagValidator = tagValidator;
        this.mapper = mapper;
        this.dateFormatter = dateFormatter;
    }

    @Override
    @Transactional
    public boolean create(Map<String, Object> certificateData) {
        if (validator.checkAllCertificateData(certificateData)) {
            Set<Tag> tags = ((ArrayList<Map<String, String>>) certificateData.get(TAGS)).stream()
                    .map(t -> t.get(NAME))
                    .map(Tag::new)
                    .collect(Collectors.toSet());
            GiftCertificate giftCertificate = new GiftCertificate.GiftCertificateBuilder()
                    .setName((String) certificateData.get(NAME))
                    .setDescription((String) certificateData.get(DESCRIPTION))
                    .setPrice(new BigDecimal((String) certificateData.get(PRICE)))
                    .setDuration(Integer.parseInt((String) certificateData.get(DURATION)))
                    .setTags(tags)
                    .build();
            repository.create(giftCertificate);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean update(Map<String, Object> certificateData) {
        if (!certificateData.isEmpty() && certificateData.containsKey(ID)
                && validator.checkId((String) certificateData.get(ID))
                && validator.checkCertificateData(certificateData)) {
            GiftCertificate giftCertificate = retrieveCertificateData(certificateData);
            giftCertificate.setId(Long.parseLong((String) certificateData.get(ID)));
            if (certificateData.containsKey(TAGS)) {
                Set<Tag> tags = ((ArrayList<Map<String, String>>) certificateData.get(TAGS)).stream()
                        .map(t -> t.get(NAME))
                        .map(Tag::new)
                        .collect(Collectors.toSet());
                giftCertificate.setTags(tags);
            }
            repository.update(giftCertificate);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(long id) {
        Optional<GiftCertificate> certificate = repository.findById(id);
        if (certificate.isPresent()) {
            repository.delete(certificate.get());
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
    public Set<GiftCertificateDto> findBySeveralParameters(Map<String, Object> certificateData, List<String> tagNames,
                                                           List<String> sortTypes) {
        if (!certificateData.isEmpty() && validator.checkCertificateData(certificateData)
                && tagValidator.checkNames(tagNames)) {
            GiftCertificate giftCertificate = retrieveCertificateData(certificateData);
            int id = certificateData.size() == 1 && certificateData.containsKey(SORT) ? 0 : 1;
            giftCertificate.setId(id);
            List<Tag> tags = new LinkedList<>();
            for (String tagName : tagNames) {
                Tag tag = new Tag(tagName);
                tags.add(tag);
            }
            Set<GiftCertificate> certificates = repository.findBySeveralParameters(giftCertificate, tags, sortTypes);
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
    private GiftCertificate retrieveCertificateData(Map<String, Object> certificateData) {
        GiftCertificate giftCertificate = new GiftCertificate();
        if (certificateData.containsKey(NAME)) {
            giftCertificate.setName((String) certificateData.get(NAME));
        }
        if (certificateData.containsKey(DESCRIPTION)) {
            giftCertificate.setDescription((String) certificateData.get(DESCRIPTION));
        }
        if (certificateData.containsKey(PRICE)) {
            giftCertificate.setPrice(new BigDecimal((String) certificateData.get(PRICE)));
        }
        if (certificateData.containsKey(DURATION)) {
            giftCertificate.setDuration(Integer.parseInt((String) certificateData.get(DURATION)));
        }
        if (certificateData.containsKey(CREATE_DATE)) {
            LocalDateTime formattedDate = dateFormatter.format((String) certificateData.get(CREATE_DATE));
            giftCertificate.setCreateDate(formattedDate);
        }
        if (certificateData.containsKey(LAST_UPDATE_DATE)) {
            LocalDateTime formattedDate = dateFormatter.format((String) certificateData.get(LAST_UPDATE_DATE));
            giftCertificate.setLastUpdateDate(formattedDate);
        }
        return giftCertificate;
    }
}
