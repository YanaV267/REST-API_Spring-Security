package com.epam.esm.repository.impl;

import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.repository.ColumnName.ORDERS;

/**
 * The type User repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    public Set<User> findAll() {
        CriteriaQuery<User> query = entityManager.getCriteriaBuilder()
                .createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        return new LinkedHashSet<>(entityManager.createQuery(query)
                .getResultList());
    }

    @Override
    public Set<User> findAllWithOrders() {
        CriteriaQuery<User> query = entityManager.getCriteriaBuilder()
                .createQuery(User.class);
        Root<User> root = query.from(User.class);
        root.join(ORDERS);
        query.select(root);
        return new LinkedHashSet<>(entityManager.createQuery(query)
                .getResultList());
    }

    @Override
    public Optional<User> findById(long id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public Set<User> findWithHighestOrderCost() {
        Query query = entityManager.createNativeQuery("SELECT id, login, surname, name, balance, max_summary FROM " +
                "(SELECT id, login, surname, name, balance, max(summary) AS max_summary FROM " +
                "(SELECT users.id AS id, login, surname, name, balance, sum(cost) AS summary FROM orders " +
                "JOIN users ON orders.id_user = users.id " +
                "GROUP BY id_user ORDER BY summary DESC) AS order_sum " +
                "ORDER BY max_summary DESC) AS max_sum", User.class);
        return new LinkedHashSet<>(query.getResultList());
    }
}