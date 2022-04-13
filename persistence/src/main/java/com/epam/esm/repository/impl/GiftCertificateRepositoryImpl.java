package com.epam.esm.repository.impl;

import com.epam.esm.builder.GiftCertificateQueryBuilder;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.GiftCertificateExtractor;
import com.epam.esm.repository.GiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The type Gift certificate repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String INSERT_CERTIFICATE = "INSERT INTO gift_certificates " +
            "(name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, now(3), now(3))";
    private static final String UPDATE_CERTIFICATE = "UPDATE gift_certificate SET ";
    private static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificates WHERE id = ?";
    private static final String SELECT_CERTIFICATES = "SELECT certificates.id, certificates.name, description, " +
            "price, duration, certificates.create_date, last_update_date, tags.id, tags.name FROM gift_certificates certificates " +
            "JOIN certificate_purchase ON certificate_purchase.id_certificate = certificates.id " +
            "JOIN tags ON certificate_purchase.id_tag = tags.id";
    private final JdbcTemplate template;
    private final GiftCertificateExtractor certificateExtractor;

    /**
     * Instantiates a new Gift certificate repository.
     *
     * @param dataSource           the data source
     * @param certificateExtractor the certificate extractor
     */
    @Autowired
    public GiftCertificateRepositoryImpl(DataSource dataSource, GiftCertificateExtractor certificateExtractor) {
        this.template = new JdbcTemplate(dataSource);
        this.certificateExtractor = certificateExtractor;
    }

    @Override
    public long create(GiftCertificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(INSERT_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, certificate.getName());
            statement.setString(2, certificate.getDescription());
            statement.setBigDecimal(3, certificate.getPrice());
            statement.setInt(4, certificate.getDuration());
            return statement;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
            return key.longValue();
        } else {
            return 0;
        }
    }

    @Override
    public void update(GiftCertificate certificate) {
        template.update(new GiftCertificateQueryBuilder(UPDATE_CERTIFICATE)
                .addNameParameter(certificate.getName())
                .addDescriptionParameter(certificate.getDescription())
                .addPriceParameter(certificate.getPrice())
                .addDurationParameter(certificate.getDuration())
                .checkQueryEnding()
                .addLastUpdateDateNowParameter()
                .addWhereClause()
                .addIdParameter(certificate.getId())
                .build());
    }

    @Override
    public void delete(long id) {
        template.update(DELETE_CERTIFICATE, id);
    }

    @Override
    public Set<GiftCertificate> findAll() {
        List<GiftCertificate> certificates = template.query(SELECT_CERTIFICATES, certificateExtractor);
        if (certificates != null) {
            return new LinkedHashSet<>(certificates);
        } else {
            return new LinkedHashSet<>();
        }
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        List<GiftCertificate> certificates = template.query(new GiftCertificateQueryBuilder(SELECT_CERTIFICATES)
                .addWhereClause()
                .addIdParameter(id)
                .build(), certificateExtractor);
        if (certificates != null) {
            return certificates.stream().findFirst();
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Set<GiftCertificate> findBySeveralParameters(GiftCertificate certificate, List<Tag> tags,
                                                        List<String> sortTypes) {
        List<GiftCertificate> certificates = template.query(
                new GiftCertificateQueryBuilder(SELECT_CERTIFICATES)
                        .addWhereClause(certificate.getId())
                        .addNameLikeParameter(certificate.getName())
                        .addDescriptionLikeParameter(certificate.getDescription())
                        .addPriceParameter(certificate.getPrice())
                        .addDurationParameter(certificate.getDuration())
                        .addCreateDateParameter(certificate.getCreateDate())
                        .addLastUpdateDateParameter(certificate.getLastUpdateDate())
                        .addTags(tags)
                        .addSorting(sortTypes)
                        .build(), certificateExtractor);
        if (certificates != null) {
            return new LinkedHashSet<>(certificates);
        } else {
            return new LinkedHashSet<>();
        }
    }
}