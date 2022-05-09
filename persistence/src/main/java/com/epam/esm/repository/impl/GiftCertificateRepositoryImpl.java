package com.epam.esm.repository.impl;

import com.epam.esm.builder.GiftCertificatePredicateBuilder;
import com.epam.esm.builder.GiftCertificateUpdateBuilder;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepositoryCustom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.epam.esm.repository.ColumnName.ID;

/**
 * The type Gift certificate repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepositoryCustom {
    @Value("${max.result.amount}")
    private int maxResultAmount;
    private int lastPage;
    private static final String UNDERSCORE = "_";
    private static final String DESC = "desc";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void update(GiftCertificate certificate) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<GiftCertificate> query = builder.createCriteriaUpdate(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        query = new GiftCertificateUpdateBuilder(query, root)
                .setName(certificate.getName())
                .setDescription(certificate.getDescription())
                .setDuration(certificate.getDuration())
                .setPrice(certificate.getPrice())
                .setLastUpdateDate()
                .build();
        query.where(builder.equal(root.get(ID), certificate.getId()));
        entityManager.createQuery(query)
                .executeUpdate();
    }

    @Override
    public Set<GiftCertificate> findBySeveralParameters(int firstElementNumber, GiftCertificate certificate,
                                                        List<String> sortTypes) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = builder.createQuery(GiftCertificate.class);
        CriteriaQuery<Long> pageQuery = builder.createQuery(Long.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        Root<GiftCertificate> pageRoot = pageQuery.from(GiftCertificate.class);

        Predicate[] predicates = new GiftCertificatePredicateBuilder(builder, root)
                .setName(certificate.getName())
                .setDescription(certificate.getDescription())
                .setDuration(certificate.getDuration())
                .setPrice(certificate.getPrice())
                .setCreateDate(certificate.getCreateDate())
                .setLastUpdateDate(certificate.getLastUpdateDate())
                .setTags(certificate.getTags())
                .build()
                .toArray(new Predicate[0]);
        Predicate[] pagePredicates = new GiftCertificatePredicateBuilder(builder, root)
                .setName(certificate.getName())
                .setDescription(certificate.getDescription())
                .setDuration(certificate.getDuration())
                .setPrice(certificate.getPrice())
                .setCreateDate(certificate.getCreateDate())
                .setLastUpdateDate(certificate.getLastUpdateDate())
                .setTags(certificate.getTags())
                .build()
                .toArray(new Predicate[0]);
        List<Order> orderList = createOrders(root, sortTypes);
        List<Order> pageOrderList = createOrders(pageRoot, sortTypes);
        query.select(root)
                .where(predicates)
                .orderBy(orderList);
        pageQuery.select(builder.count(pageRoot))
                .where(pagePredicates)
                .orderBy(pageOrderList);

        long amount = entityManager.createQuery(pageQuery).getSingleResult();
        lastPage = (int) Math.ceil((double) amount / maxResultAmount);
        return new LinkedHashSet<>(entityManager.createQuery(query)
                .setFirstResult(firstElementNumber)
                .setMaxResults(maxResultAmount)
                .getResultList());
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