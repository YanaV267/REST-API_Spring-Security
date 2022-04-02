package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.impl.GiftCertificateMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.CertificateDateFormatter;
import com.epam.esm.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.util.ParameterName.*;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository repository;
    private final CertificateDateFormatter dateFormatter;
    private final GiftCertificateValidator validator;
    private final GiftCertificateMapper mapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository repository, CertificateDateFormatter dateFormatter,
                                      GiftCertificateValidator validator,
                                      @Qualifier("certificateMapper") GiftCertificateMapper mapper) {
        this.repository = repository;
        this.dateFormatter = dateFormatter;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public boolean create(Map<String, String> certificateData) {
        if (validator.checkCertificate(certificateData)) {
            GiftCertificate giftCertificate = new GiftCertificate.GiftCertificateBuilder()
                    .setName(certificateData.get(NAME))
                    .setDescription(certificateData.get(DESCRIPTION))
                    .setPrice(new BigDecimal(certificateData.get(PRICE)))
                    .setDuration(Integer.parseInt(certificateData.get(DURATION)))
                    .setCreateDate(dateFormatter.format(certificateData.get(CREATE_DATE)))
                    .setLastUpdateDate(dateFormatter.format(certificateData.get(LAST_UPDATE_DATE)))
                    .build();
            return repository.create(giftCertificate);
        } else {
            return false;
        }
    }

    @Override
    public boolean update(Map<String, String> certificateData) {
        if (validator.checkCertificate(certificateData)) {
            GiftCertificate giftCertificate = new GiftCertificate.GiftCertificateBuilder()
                    .setId(Long.parseLong(certificateData.get(ID)))
                    .setName(certificateData.get(NAME))
                    .setDescription(certificateData.get(DESCRIPTION))
                    .setPrice(new BigDecimal(certificateData.get(PRICE)))
                    .setDuration(Integer.parseInt(certificateData.get(DURATION)))
                    .setCreateDate(dateFormatter.format(certificateData.get(CREATE_DATE)))
                    .setLastUpdateDate(dateFormatter.format(certificateData.get(LAST_UPDATE_DATE)))
                    .build();
            return repository.update(giftCertificate);
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
