package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.repository.ColumnName.NAME;

/**
 * The type Tag repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Repository
public class TagRepositoryImpl implements TagRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long create(Tag tag) {
        entityManager.persist(tag);
        return tag.getId();
    }

    @Override
    public void delete(Tag tag) {
        entityManager.remove(tag);
    }

    @Override
    public Set<Tag> findAll(int firstElementNumber) {
        CriteriaQuery<Tag> query = entityManager.getCriteriaBuilder()
                .createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        query.select(root);
        return new LinkedHashSet<>(entityManager.createQuery(query)
                .setFirstResult(firstElementNumber)
                .setMaxResults(MAX_RESULT_AMOUNT)
                .getResultList());
    }

    @Override
    public Optional<Tag> findById(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        return Optional.ofNullable(tag);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = entityManager.getCriteriaBuilder()
                .createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        try {
            query.select(root)
                    .where(builder.equal(root.get(NAME), name));
            return Optional.of(entityManager.createQuery(query)
                    .getSingleResult());
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Set<Tag> findMostUsedTag(int firstElementNumber) {
        Query query = entityManager.createNativeQuery("SELECT id, name FROM " +
                "(SELECT max(amount), id, name FROM " +
                "(SELECT o.id_user, count(*) AS amount, t.id AS id, t.name as name FROM tags t " +
                "JOIN certificate_purchase cp ON t.id = cp.id_tag " +
                "JOIN gift_certificates gc ON gc.id = cp.id_certificate " +
                "JOIN orders o ON o.id_certificate = gc.id " +
                "GROUP BY id_user, t.name ORDER BY amount DESC) AS tag_amount " +
                "WHERE id_user = " +
                "(SELECT id_user FROM " +
                "(SELECT id_user, max(summary) FROM " +
                "(SELECT id_user, sum(cost) AS summary FROM orders " +
                "GROUP BY id_user) AS order_sum) " +
                "AS max_sum) " +
                "GROUP BY id_user, name) AS most_used", Tag.class);
        return new LinkedHashSet<>(query
                .setFirstResult(firstElementNumber)
                .setMaxResults(MAX_RESULT_AMOUNT)
                .getResultList());
    }
}
