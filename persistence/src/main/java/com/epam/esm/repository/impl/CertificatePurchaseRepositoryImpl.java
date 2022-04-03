package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.CertificatePurchaseRepository;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class CertificatePurchaseRepositoryImpl implements CertificatePurchaseRepository {
    private static final String INSERT_PURCHASE = "INSERT INTO certificate_purchase (id_certificate, id_tag) VALUES (?, ?)";

    private final JdbcTemplate template;
    private final GiftCertificateRepository certificateRepository;
    private final TagRepository tagRepository;

    @Autowired
    public CertificatePurchaseRepositoryImpl(DataSource dataSource, GiftCertificateRepository certificateRepository,
                                             TagRepository tagRepository) {
        this.template = new JdbcTemplate(dataSource);
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public boolean create(GiftCertificate certificate) {
        long certificateId = certificateRepository.create(certificate);
        certificate.getTags().forEach(tag -> {
            Optional<Tag> foundTag = tagRepository.findByName(tag.getName());
            long tagId;
            tagId = foundTag.map(Tag::getId).orElseGet(() -> tagRepository.create(tag));
            template.update(INSERT_PURCHASE, certificateId, tagId);
        });
        return true;
    }

    @Override
    public boolean update(GiftCertificate certificate) {
        certificateRepository.update(certificate);
        certificate.getTags().forEach(tag -> {
            Optional<Tag> foundTag = tagRepository.findByName(tag.getName());
            if (!foundTag.isPresent()) {
                long tagId = tagRepository.create(tag);
                template.update(INSERT_PURCHASE, certificate.getId(), tagId);
            }
        });
        return true;
    }
}
