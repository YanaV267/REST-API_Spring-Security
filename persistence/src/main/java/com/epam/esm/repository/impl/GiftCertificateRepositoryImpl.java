package com.epam.esm.repository.impl;

import com.epam.esm.connection.ConnectionPool;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.repository.ColumnName.*;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final Logger LOGGER = LogManager.getLogger(GiftCertificateRepositoryImpl.class);
    private static final String INSERT_CERTIFICATE = "INSERT INTO gift_certificate " +
            "(name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CERTIFICATE = "UPDATE gift_certificate " +
            "SET name = ?, description = ?, price = ?, duration = ?, create_date = ?, last_update_date = ? WHERE id = ?";
    private static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String SELECT_ALL_CERTIFICATES = "SELECT id, name, description, price, duration, create_date, " +
            "last_update_date FROM gift_certificate";
    private static final String SELECT_CERTIFICATE_BY_ID = "SELECT id, name, description, price, duration, create_date, " +
            "last_update_date FROM gift_certificate WHERE id = ?";
    private static final String SELECT_CERTIFICATES_BY_NAME = "SELECT id, name, description, price, duration, create_date, " +
            "last_update_date FROM gift_certificate WHERE name = ?";
    private static final String SELECT_CERTIFICATES_BY_DESCRIPTION = "SELECT id, name, description, price, duration, create_date, " +
            "last_update_date FROM gift_certificate WHERE description = ?";

    private final ConnectionPool connectionPool;

    public GiftCertificateRepositoryImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public boolean create(GiftCertificate certificate) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_CERTIFICATE)) {
            statement.setString(1, certificate.getName());
            statement.setString(2, certificate.getDescription());
            statement.setBigDecimal(3, certificate.getPrice());
            statement.setInt(4, certificate.getDuration());
            statement.setDate(5, new Date(certificate.getCreateDate().getTime()));
            statement.setDate(6, new Date(certificate.getLastUpdateDate().getTime()));
            statement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while creating gift certificate: " + exception);
            return false;
        }
    }

    @Override
    public boolean update(GiftCertificate certificate) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CERTIFICATE)) {
            statement.setString(1, certificate.getName());
            statement.setString(2, certificate.getDescription());
            statement.setBigDecimal(3, certificate.getPrice());
            statement.setInt(4, certificate.getDuration());
            statement.setDate(5, new Date(certificate.getCreateDate().getTime()));
            statement.setDate(6, new Date(certificate.getLastUpdateDate().getTime()));
            statement.setLong(7, certificate.getId());
            statement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating gift certificate data: " + exception);
            return false;
        }
    }

    @Override
    public boolean delete(long id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CERTIFICATE)) {
            statement.setLong(1, id);
            statement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while deleting gift certificate: " + exception);
            return false;
        }
    }

    @Override
    public List<GiftCertificate> findAll() {
        List<GiftCertificate> certificates = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_CERTIFICATES)) {
            certificates = retrieve(resultSet);
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding all gift certificates: " + exception);
        }
        return certificates;
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CERTIFICATE_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<GiftCertificate> certificates = retrieve(resultSet);
                if (!certificates.isEmpty()) {
                    return Optional.of(certificates.get(0));
                }
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding gift certificate by id: " + exception);
        }
        return Optional.empty();
    }

    @Override
    public List<GiftCertificate> findByName(String name) {
        List<GiftCertificate> certificates = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CERTIFICATES_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                certificates = retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding gift certificates by name: " + exception);
        }
        return certificates;
    }

    @Override
    public List<GiftCertificate> findByDescription(String description) {
        List<GiftCertificate> certificates = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CERTIFICATES_BY_DESCRIPTION)) {
            statement.setString(1, description);
            try (ResultSet resultSet = statement.executeQuery()) {
                certificates = retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding gift certificates by description: " + exception);
        }
        return certificates;
    }

    public List<GiftCertificate> retrieve(ResultSet resultSet) throws SQLException {
        List<GiftCertificate> certificates = new ArrayList<>();
        while (resultSet.next()) {
            GiftCertificate certificate = new GiftCertificate.GiftCertificateBuilder()
                    .setId(resultSet.getLong(ID))
                    .setName(resultSet.getString(NAME))
                    .setDescription(resultSet.getString(DESCRIPTION))
                    .setPrice(resultSet.getBigDecimal(PRICE))
                    .setDuration(resultSet.getInt(DURATION))
                    .setCreateDate(resultSet.getDate(CREATE_DATE))
                    .setLastUpdateDate(resultSet.getDate(LAST_UPDATE_DATE))
                    .build();
            certificates.add(certificate);
        }
        return certificates;
    }
}
