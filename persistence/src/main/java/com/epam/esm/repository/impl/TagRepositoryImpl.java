package com.epam.esm.repository.impl;

import com.epam.esm.connection.ConnectionPool;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.repository.ColumnName.ID;
import static com.epam.esm.repository.ColumnName.NAME;

@Repository
public class TagRepositoryImpl implements TagRepository {
    private static final Logger LOGGER = LogManager.getLogger(GiftCertificateRepositoryImpl.class);
    private static final String INSERT_TAG = "INSERT INTO tag (name) VALUES (?)";
    private static final String DELETE_TAG = "DELETE FROM tag WHERE id = ?";
    private static final String SELECT_ALL_TAGS = "SELECT id, name FROM tag";
    private static final String SELECT_TAG_BY_ID = "SELECT id, name FROM tag WHERE id = ?";
    private static final String SELECT_TAG_BY_NAME = "SELECT id, name FROM tag WHERE name = ?";

    private final ConnectionPool connectionPool;

    @Autowired
    public TagRepositoryImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public boolean create(Tag tag) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_TAG)) {
            statement.setString(1, tag.getName());
            statement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while creating gift certificate: " + exception);
            return false;
        }
    }

    @Override
    public boolean delete(long id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_TAG)) {
            statement.setLong(1, id);
            statement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred deleting gift certificate: " + exception);
            return false;
        }
    }

    @Override
    public List<Tag> findAll() {
        List<Tag> tags = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_TAGS)) {
            while (resultSet.next()) {
                Tag tag = new Tag(resultSet.getLong(ID), resultSet.getString(NAME));
                tags.add(tag);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred finding all gift certificates: " + exception);
        }
        return tags;
    }

    @Override
    public Optional<Tag> findById(long id) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_TAG_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Tag tag = new Tag(resultSet.getLong(ID), resultSet.getString(NAME));
                    return Optional.of(tag);
                }
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred deleting gift certificate: " + exception);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_TAG_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Tag tag = new Tag(resultSet.getLong(ID), resultSet.getString(NAME));
                    return Optional.of(tag);
                }
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred deleting gift certificate: " + exception);
        }
        return Optional.empty();
    }
}
