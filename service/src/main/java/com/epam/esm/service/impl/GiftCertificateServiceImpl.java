package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.impl.GiftCertificateMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Gift certificate service.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private int lastPage;
    private final GiftCertificateRepository repository;
    private final TagRepository tagRepository;
    private final GiftCertificateMapper mapper;

    /**
     * Instantiates a new Gift certificate service.
     *
     * @param repository    the repository
     * @param tagRepository the tag repository
     * @param mapper        the mapper
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository repository, TagRepository tagRepository,
                                      @Qualifier("certificateServiceMapper") GiftCertificateMapper mapper) {
        this.repository = repository;
        this.tagRepository = tagRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public boolean create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate certificate = mapper.mapToEntity(giftCertificateDto);
        certificate.getTags().forEach(t -> {
            Optional<Tag> tag = tagRepository.findByName(t.getName());
            tag.ifPresent(value -> t.setId(value.getId()));
        });
        repository.create(certificate);
        return true;
    }

    @Override
    @Transactional
    public boolean update(GiftCertificateDto giftCertificateDto) {
        Optional<GiftCertificate> foundCertificate = repository.findById(giftCertificateDto.getId());
        if (foundCertificate.isPresent()) {
            GiftCertificate certificate = mapper.mapToEntity(giftCertificateDto);
            repository.update(certificate);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
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
    public Set<GiftCertificateDto> findAll(int page) {
        int firstElementNumber = getFirstElementNumber(page);
        Set<GiftCertificate> certificates = repository.findAll(firstElementNumber);
        lastPage = repository.getLastPage();
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
    public Set<GiftCertificateDto> findBySeveralParameters(int page, GiftCertificateDto certificateDto,
                                                           List<String> tagNames, List<String> sortTypes) {
        GiftCertificate giftCertificate = mapper.mapToEntity(certificateDto);
        if (tagNames != null) {
            Set<Tag> tags = tagNames.stream()
                    .map(Tag::new)
                    .collect(Collectors.toSet());
            giftCertificate.setTags(tags);
        }
        int firstElementNumber = getFirstElementNumber(page);
        Set<GiftCertificate> certificates = repository.findBySeveralParameters(firstElementNumber,
                giftCertificate, sortTypes);
        lastPage = repository.getLastPage();
        return certificates.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public int getLastPage() {
        return lastPage;
    }
}
