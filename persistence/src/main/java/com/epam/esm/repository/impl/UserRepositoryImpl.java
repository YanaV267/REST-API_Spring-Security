package com.epam.esm.repository.impl;

import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepositoryCustom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type User repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Component
public class UserRepositoryImpl implements UserRepositoryCustom {
    @Value("${max.result.amount}")
    private int maxResultAmount;
    private int lastPage;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<User> findWithHighestOrderCost(int firstElementNumber) {
        String querySql = "FROM (SELECT id, login, surname, name, balance, max(summary) AS max_summary FROM " +
                "(SELECT users.id AS id, login, surname, name, balance, sum(cost) AS summary FROM orders " +
                "JOIN users ON orders.id_user = users.id " +
                "GROUP BY id_user ORDER BY summary DESC) AS order_sum " +
                "ORDER BY max_summary DESC) AS max_sum";
        Query query = entityManager.createNativeQuery("SELECT id, login, surname, name, balance, max_summary "
                + querySql, User.class);
        Query pageQuery = entityManager.createNativeQuery("SELECT COUNT(id) " + querySql);
        BigInteger amount = (BigInteger) pageQuery.getSingleResult();
        lastPage = (int) Math.ceil((double) amount.longValue() / maxResultAmount);
        return new LinkedHashSet<>(query
                .setFirstResult(firstElementNumber)
                .setMaxResults(maxResultAmount)
                .getResultList());
    }

    @Override
    public Set<User> findWithHighestOrderCostMostUsedTag(int firstElementNumber) {
        String querySql = "FROM (SELECT id_user as id, login, surname, name, balance, id_tag, max(summary) " +
                "FROM (SELECT id_user, login, surname, u.name as name, balance, t.id as id_tag, sum(cost) AS summary " +
                "FROM orders " +
                "JOIN certificate_purchase p on orders.id = p.id_order " +
                "JOIN users u on u.id = orders.id_user " +
                "JOIN gift_certificates g on g.id = p.id_certificate " +
                "JOIN tag_reference c on g.id = c.id_certificate " +
                "JOIN tags t on t.id = c.id_tag " +
                "GROUP BY id_user) AS order_sum " +
                "WHERE id_tag = " +
                "(SELECT id " +
                "FROM (SELECT max(amount), id " +
                "FROM (SELECT count(*) AS amount, t.id AS id " +
                "FROM tags t " +
                "JOIN tag_reference cp ON t.id = cp.id_tag " +
                "JOIN gift_certificates gc ON gc.id = cp.id_certificate " +
                "JOIN certificate_purchase op ON op.id_certificate = gc.id " +
                "JOIN orders o ON op.id_order = o.id " +
                "GROUP BY t.name " +
                "ORDER BY amount DESC) AS tag_amount) " +
                "AS max_amount) " +
                "GROUP BY id_tag) AS most_used";
        Query query = entityManager.createNativeQuery("SELECT id, login, surname, name, balance "
                + querySql, User.class);
        Query pageQuery = entityManager.createNativeQuery("SELECT COUNT(id) " + querySql);
        BigInteger amount = (BigInteger) pageQuery.getSingleResult();
        lastPage = (int) Math.ceil((double) amount.longValue() / maxResultAmount);
        return new LinkedHashSet<>(query
                .setFirstResult(firstElementNumber)
                .setMaxResults(maxResultAmount)
                .getResultList());
    }

    @Override
    public int getLastPage() {
        return lastPage;
    }
}