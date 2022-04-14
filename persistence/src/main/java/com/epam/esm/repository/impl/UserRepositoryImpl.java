package com.epam.esm.repository.impl;

import com.epam.esm.entity.User;
import com.epam.esm.mapper.UserExtractor;
import com.epam.esm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The type User repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_USERS = "SELECT users.id, login, users.name, surname, balance FROM users";
    private static final String SELECT_USERS_WITH_ORDERS = "SELECT users.id, login, users.name, surname, balance," +
            "certificates.id, certificates.name, description, price, duration, certificates.create_date, last_update_date, " +
            "orders.id, cost, orders.create_date, tags.id, tags.name FROM users " +
            "JOIN orders on users.id = orders.id_user " +
            "JOIN gift_certificates certificates on orders.id_certificate = certificates.id " +
            "JOIN certificate_purchase on certificates.id = certificate_purchase.id_certificate " +
            "JOIN tags on certificate_purchase.id_tag = tags.id";
    private static final String SELECT_USERS_BY_ID = "SELECT users.id, login, users.name, surname, balance " +
            "FROM users WHERE users.id = ";
    private final JdbcTemplate template;
    private final UserExtractor userExtractor;

    /**
     * Instantiates a new User repository.
     *
     * @param dataSource    the data source
     * @param userExtractor the user extractor
     */
    @Autowired
    public UserRepositoryImpl(DataSource dataSource, UserExtractor userExtractor) {
        this.template = new JdbcTemplate(dataSource);
        this.userExtractor = userExtractor;
    }

    @Override
    public void delete(long id) {
        template.update(DELETE_USER, id);
    }

    @Override
    public Set<User> findAll() {
        List<User> users = template.query(SELECT_USERS, userExtractor);
        if (users != null) {
            return new LinkedHashSet<>(users);
        } else {
            return new LinkedHashSet<>();
        }
    }

    @Override
    public Set<User> findAllWithOrders() {
        List<User> users = template.query(SELECT_USERS_WITH_ORDERS, userExtractor);
        if (users != null) {
            return new LinkedHashSet<>(users);
        } else {
            return new LinkedHashSet<>();
        }
    }

    @Override
    public Optional<User> findById(long id) {
        List<User> users = template.query(SELECT_USERS_BY_ID.concat(String.valueOf(id)), userExtractor);
        if (users != null) {
            return users.stream().findFirst();
        } else {
            return Optional.empty();
        }
    }
}