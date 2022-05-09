package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepositoryCustom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Tag repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Repository
public class TagRepositoryImpl implements TagRepositoryCustom {
    @Value("${max.result.amount}")
    private int maxResultAmount;
    private int lastPage;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Tag> findMostUsedTag(int firstElementNumber) {
        String querySql = "FROM (SELECT max(amount), id, name FROM " +
                "(SELECT o.id_user, count(*) AS amount, t.id AS id, t.name as name FROM tags t " +
                "JOIN tag_reference cp ON t.id = cp.id_tag " +
                "JOIN gift_certificates gc ON gc.id = cp.id_certificate " +
                "JOIN certificate_purchase op ON op.id_certificate = gc.id " +
                "JOIN orders o ON op.id_order = o.id " +
                "GROUP BY id_user, t.name ORDER BY amount DESC) AS tag_amount " +
                "WHERE id_user = " +
                "(SELECT id_user FROM " +
                "(SELECT id_user, max(summary) FROM " +
                "(SELECT id_user, sum(cost) AS summary FROM orders " +
                "GROUP BY id_user) AS order_sum) " +
                "AS max_sum) " +
                "GROUP BY id_user, name) AS most_used";
        Query query = entityManager.createNativeQuery("SELECT id, name " + querySql, Tag.class);
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
