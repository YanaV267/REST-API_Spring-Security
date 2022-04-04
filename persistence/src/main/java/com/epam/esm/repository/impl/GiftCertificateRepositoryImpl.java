package com.epam.esm.repository.impl;

import com.epam.esm.builder.GiftCertificateQueryBuilder;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.GiftCertificateExtractor;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String INSERT_CERTIFICATE = "INSERT INTO gift_certificate " +
            "(name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, now(3), now(3))";
    private static final String UPDATE_CERTIFICATE = "UPDATE gift_certificate SET ";
    private static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String SELECT_CERTIFICATES = "SELECT gift_certificate.id, gift_certificate.name, description, " +
            "price, duration, create_date, last_update_date, tag.id, tag.name FROM gift_certificate " +
            "JOIN certificate_purchase ON certificate_purchase.id_certificate = gift_certificate.id " +
            "JOIN tag ON certificate_purchase.id_tag = tag.id";
    private final JdbcTemplate template;

    @Autowired
    public GiftCertificateRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
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
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(GiftCertificate certificate) {
        template.update(new GiftCertificateQueryBuilder(UPDATE_CERTIFICATE)
                .addNameParameter(certificate.getName())
                .addDescriptionParameter(certificate.getDescription())
                .addPriceParameter(certificate.getPrice())
                .addDurationParameter(certificate.getDuration())
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
        List<GiftCertificate> certificates = template.query(SELECT_CERTIFICATES, new GiftCertificateExtractor());
        if (certificates != null) {
            return new LinkedHashSet<>(certificates);
        } else {
            return new LinkedHashSet<>();
        }
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        try {
            GiftCertificate certificate = template.queryForObject(
                    new GiftCertificateQueryBuilder(SELECT_CERTIFICATES)
                            .addWhereClause()
                            .addIdParameter(id)
                            .build(), new GiftCertificateMapper());
            return Optional.ofNullable(certificate);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Set<GiftCertificate> findBySeveralParameters(GiftCertificate certificate, String tagName,
                                                        List<String> sortTypes) {
        List<GiftCertificate> certificates = template.query(
                new GiftCertificateQueryBuilder(SELECT_CERTIFICATES)
                        .addWhereClause()
                        .addNameLikeParameter(certificate.getName())
                        .addDescriptionLikeParameter(certificate.getDescription())
                        .addPriceParameter(certificate.getPrice())
                        .addDurationParameter(certificate.getDuration())
                        .addCreateDateParameter(certificate.getCreateDate())
                        .addLastUpdateDateParameter(certificate.getLastUpdateDate())
                        .addTagName(tagName)
                        .addSorting(sortTypes)
                        .build(), new GiftCertificateExtractor());
        if (certificates != null) {
            return new LinkedHashSet<>(certificates);
        } else {
            return new LinkedHashSet<>();
        }
    }
}