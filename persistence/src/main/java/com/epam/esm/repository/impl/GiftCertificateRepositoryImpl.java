package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
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
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String INSERT_CERTIFICATE = "INSERT INTO gift_certificate " +
            "(name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, now(), now())";
    private static final String UPDATE_CERTIFICATE = "UPDATE gift_certificate " +
            "SET name = ?, description = ?, price = ?, duration = ?, create_date = ?, last_update_date = now() WHERE id = ?";
    private static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String SELECT_ALL_CERTIFICATES = "SELECT id, name, description, price, duration, create_date, " +
            "last_update_date FROM gift_certificate";
    private static final String SELECT_CERTIFICATE_BY_ID = "SELECT id, name, description, price, duration, create_date, " +
            "last_update_date FROM gift_certificate WHERE id = ?";
    private static final String SELECT_CERTIFICATES_BY_NAME = "SELECT id, name, description, price, duration, create_date, " +
            "last_update_date FROM gift_certificate WHERE name = ?";
    private static final String SELECT_CERTIFICATES_BY_DESCRIPTION = "SELECT id, name, description, price, duration, create_date, " +
            "last_update_date FROM gift_certificate WHERE description = ?";

    private final JdbcTemplate template;

    @Autowired
    public GiftCertificateRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public long create(GiftCertificate certificate) {
//        template.update(INSERT_CERTIFICATE, certificate.getName(), certificate.getDescription(), certificate.getPrice(),
//                certificate.getDuration());
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
    public boolean update(GiftCertificate certificate) {
        template.update(UPDATE_CERTIFICATE, certificate.getName(), certificate.getDescription(), certificate.getPrice(),
                certificate.getDuration(), certificate.getId());
        return true;
    }

    @Override
    public boolean delete(long id) {
        template.update(DELETE_CERTIFICATE, id);
        return true;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return template.query(SELECT_ALL_CERTIFICATES, new GiftCertificateMapper());
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        try {
            GiftCertificate certificate = template.queryForObject(SELECT_CERTIFICATE_BY_ID, new GiftCertificateMapper(), id);
            return Optional.ofNullable(certificate);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<GiftCertificate> findByName(String name) {
        return template.query(SELECT_CERTIFICATES_BY_NAME, new GiftCertificateMapper(), name);
    }

    @Override
    public List<GiftCertificate> findByDescription(String description) {
        return template.query(SELECT_CERTIFICATES_BY_DESCRIPTION, new GiftCertificateMapper(), description);
    }
}