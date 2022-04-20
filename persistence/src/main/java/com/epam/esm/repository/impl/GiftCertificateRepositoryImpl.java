package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
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
    private int lastPage;
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
        query = query.set(root.get(LAST_UPDATE_DATE), LocalDateTime.now());
        query.where(builder.equal(root.get(ID), certificate.getId()));
        entityManager.createQuery(query)
                .executeUpdate();
    }

    @Override
    public void delete(GiftCertificate certificate) {
        entityManager.remove(certificate);
    }

    @Override
    public Set<GiftCertificate> findAll(int firstElementNumber) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = builder.createQuery(GiftCertificate.class);
        CriteriaQuery<Long> pageQuery = builder.createQuery(Long.class);

        query.select(query.from(GiftCertificate.class));
        pageQuery.select(builder.count(pageQuery.from(GiftCertificate.class)));

        long amount = entityManager.createQuery(pageQuery).getSingleResult();
        lastPage = (int) Math.ceil((double) amount / MAX_RESULT_AMOUNT);
        return new LinkedHashSet<>(entityManager.createQuery(query)
                .setFirstResult(firstElementNumber)
                .setMaxResults(MAX_RESULT_AMOUNT)
                .getResultList());
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        GiftCertificate certificate = entityManager.find(GiftCertificate.class, id);
        return Optional.ofNullable(certificate);
    }

    @Override
    public Set<GiftCertificate> findBySeveralParameters(int firstElementNumber, GiftCertificate certificate, Set<Tag> tags,
                                                        List<String> sortTypes) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = builder.createQuery(GiftCertificate.class);
        CriteriaQuery<Long> pageQuery = builder.createQuery(Long.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        Root<GiftCertificate> pageRoot = pageQuery.from(GiftCertificate.class);

        Predicate[] predicates = createPredicates(root, certificate, tags).toArray(new Predicate[0]);
        Predicate[] pagePredicates = createPredicates(pageRoot, certificate, tags).toArray(new Predicate[0]);
        List<Order> orderList = createOrders(root, sortTypes);
        List<Order> pageOrderList = createOrders(pageRoot, sortTypes);
        query.select(root)
                .where(predicates)
                .orderBy(orderList);
        pageQuery.select(builder.count(pageRoot))
                .where(pagePredicates)
                .orderBy(pageOrderList);

        long amount = entityManager.createQuery(pageQuery).getSingleResult();
        lastPage = (int) Math.ceil((double) amount / MAX_RESULT_AMOUNT);
        return new LinkedHashSet<>(entityManager.createQuery(query)
                .setFirstResult(firstElementNumber)
                .setMaxResults(MAX_RESULT_AMOUNT)
                .getResultList());
    }

    private List<Predicate> createPredicates(Root<GiftCertificate> root,
                                             GiftCertificate certificate, Set<Tag> tags) {
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
                predicates.add(builder.like(root.join(TAGS).get(NAME), tag.getName()));
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

    @Override
    public int getLastPage() {
        return lastPage;
    }
}