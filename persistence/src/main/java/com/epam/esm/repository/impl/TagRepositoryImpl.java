package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
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
    public Set<Tag> findAll() {
        CriteriaQuery<Tag> query = entityManager.getCriteriaBuilder()
                .createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        query.select(root);
        return new LinkedHashSet<>(entityManager.createQuery(query)
                .getResultList());
    }

    @Override
    public Optional<Tag> findById(long id) {
        try {
            Tag tag = entityManager.find(Tag.class, id);
            return Optional.of(tag);
        } catch (NoResultException exception) {
            return Optional.empty();
        }
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
}
