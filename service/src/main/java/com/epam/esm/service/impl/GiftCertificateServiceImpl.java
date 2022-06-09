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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * The type Gift certificate service.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final String UNDERSCORE = "_";
    private static final String DESC = "desc";
    private final GiftCertificateRepository repository;
    private final TagRepository tagRepository;
    private final GiftCertificateMapper mapper;
    @Value("${max.result.amount}")
    private int maxResultAmount;
    private int lastPage;

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
        repository.save(certificate);
        return true;
    }

    @Override
    @Transactional
    public boolean update(GiftCertificateDto giftCertificateDto) {
        Optional<GiftCertificate> foundCertificate = repository.findById(giftCertificateDto.getId());
        if (foundCertificate.isPresent()) {
            GiftCertificate certificate = mapper.mapToEntity(giftCertificateDto);
            repository.save(certificate);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        boolean exists = repository.existsById(id);
        if (exists) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Set<GiftCertificateDto> findAll(int page) {
        Pageable pageable = PageRequest.of(page, maxResultAmount);
        Set<GiftCertificate> certificates = repository.findAll(pageable).toSet();
        lastPage = repository.findAll(pageable).getTotalPages();
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
        Pageable pageable = PageRequest.of(page, maxResultAmount, Sort.by(createOrders(sortTypes)));
        Set<GiftCertificate> certificates = repository.findAll(Example.of(giftCertificate), pageable).toSet();
        lastPage = repository.findAll(Example.of(giftCertificate), pageable).getTotalPages();
        return certificates.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private List<Sort.Order> createOrders(List<String> sortTypes) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortTypes != null) {
            for (String sortType : sortTypes) {
                String type = sortType.substring(sortType.indexOf(UNDERSCORE));
                String column = sortType.substring(0, sortType.indexOf(UNDERSCORE));
                if (DESC.equals(type)) {
                    orders.add(Sort.Order.desc(column));
                } else {
                    orders.add(Sort.Order.asc(column));
                }
            }
        }
        return orders;
    }

    @Override
    public int getLastPage() {
        return lastPage;
    }
}
