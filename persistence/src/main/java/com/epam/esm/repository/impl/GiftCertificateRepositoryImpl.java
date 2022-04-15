package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.*;

import static com.epam.esm.repository.ColumnName.*;

/**
 * The type Gift certificate repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String UNDERSCORE = "_";
    private static final String DESC = "desc";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long create(GiftCertificate certificate) {
        entityManager.persist(certificate);
        return certificate.getId();
    }

    @Override
    public void update(GiftCertificate certificate) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<GiftCertificate> query = entityManager.getCriteriaBuilder()
                .createCriteriaUpdate(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        if (certificate.getName() != null) {
            query = query.set(root.get(NAME), certificate.getName());
        }
        if (certificate.getDescription() != null) {
            query = query.set(root.get(DESCRIPTION), certificate.getDescription());
        }
        if (certificate.getPrice() != null) {
            query = query.set(root.get(PRICE), certificate.getPrice());
        }
        if (certificate.getDuration() != 0) {
            query = query.set(root.get(DURATION), certificate.getDuration());
        }
        query.where(builder.equal(root.get(ID), certificate.getId()));
        entityManager.createQuery(query)
                .executeUpdate();
    }

    @Override
    public void delete(GiftCertificate certificate) {
        entityManager.remove(certificate);
    }

    @Override
    public Set<GiftCertificate> findAll() {
        CriteriaQuery<GiftCertificate> query = entityManager.getCriteriaBuilder()
                .createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        query.select(root);
        return new LinkedHashSet<>(entityManager.createQuery(query)
                .getResultList());
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        GiftCertificate certificate = entityManager.find(GiftCertificate.class, id);
        return Optional.ofNullable(certificate);
    }

    @Override
    public Set<GiftCertificate> findBySeveralParameters(GiftCertificate certificate, List<Tag> tags,
                                                        List<String> sortTypes) {
        CriteriaQuery<GiftCertificate> query = entityManager.getCriteriaBuilder()
                .createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        Predicate[] predicates = createPredicates(root, certificate, tags)
                .toArray(new Predicate[0]);
        List<Order> orderList = createOrders(root, sortTypes);
        query.select(root)
                .where(predicates)
                .orderBy(orderList);
        return new LinkedHashSet<>(entityManager.createQuery(query)
                .getResultList());
    }

    private List<Predicate> createPredicates(Root<GiftCertificate> root,
                                             GiftCertificate certificate, List<Tag> tags) {
        Join<GiftCertificate, Tag> joinTags = root.join(TAGS);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();
        if (certificate.getName() != null) {
            predicates.add(builder.like(root.get(NAME), certificate.getName()));
        }
        if (certificate.getDescription() != null) {
            predicates.add(builder.like(root.get(DESCRIPTION), certificate.getDescription()));
        }
        if (certificate.getPrice() != null) {
            predicates.add(builder.equal(root.get(PRICE), certificate.getPrice()));
        }
        if (certificate.getDuration() != 0) {
            predicates.add(builder.equal(root.get(DURATION), certificate.getDuration()));
        }
        if (certificate.getCreateDate() != null) {
            predicates.add(builder.equal(root.get(CREATE_DATE), certificate.getCreateDate()));
        }
        if (certificate.getLastUpdateDate() != null) {
            predicates.add(builder.equal(root.get(LAST_UPDATE_DATE), certificate.getLastUpdateDate()));
        }
        if (tags != null) {
            for (Tag tag : tags) {
                predicates.add(builder.like(joinTags.get(NAME), tag.getName()));
            }
        }
        return predicates;
    }

    private List<Order> createOrders(Root<GiftCertificate> root, List<String> sortTypes) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        List<Order> orders = new ArrayList<>();
        if (sortTypes != null) {
            for (String sortType : sortTypes) {
                String type = sortType.substring(sortType.indexOf(UNDERSCORE));
                String column = sortType.substring(0, sortType.indexOf(UNDERSCORE));
                if (type.equals(DESC)) {
                    orders.add(builder.desc(root.get(column)));
                } else {
                    orders.add(builder.asc(root.get(column)));
                }
            }
        }
        return orders;
    }
}