package com.epam.esm.builder;

import com.epam.esm.entity.GiftCertificatePurchase;
import com.epam.esm.entity.Order;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.epam.esm.repository.ColumnName.*;

/**
 * The type Order predicate builder.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public class OrderPredicateBuilder {
    private final List<Predicate> predicates;
    private final Root<Order> root;
    private final CriteriaBuilder builder;

    /**
     * Instantiates a new Order predicate builder.
     *
     * @param builder the builder
     * @param root    the root
     */
    public OrderPredicateBuilder(CriteriaBuilder builder, Root<Order> root) {
        predicates = new ArrayList<>();
        this.builder = builder;
        this.root = root;
    }

    /**
     * Sets user.
     *
     * @param userIds the user ids
     * @return the user
     */
    public OrderPredicateBuilder setUser(List<Integer> userIds) {
        List<Predicate> userPredicates = new ArrayList<>();
        if (userIds != null) {
            for (Integer userId : userIds) {
                userPredicates.add(builder.equal(root.join(USER).get(ID), userId));
            }
        }
        predicates.add(builder.or(userPredicates.toArray(new Predicate[0])));
        return this;
    }

    /**
     * Sets certificatePurchase.
     *
     * @param certificatePurchase the certificatePurchase
     * @return the certificatePurchase
     */
    public OrderPredicateBuilder setCertificates(Set<GiftCertificatePurchase> certificatePurchase) {
        if (certificatePurchase != null) {
            for (GiftCertificatePurchase certificate : certificatePurchase) {
                predicates.add(builder.equal(root.join(CERTIFICATES).get(ID), certificate.getCertificate().getId()));
            }
        }
        return this;
    }

    /**
     * Sets cost.
     *
     * @param cost the cost
     * @return the cost
     */
    public OrderPredicateBuilder setCost(BigDecimal cost) {
        if (cost != null) {
            predicates.add(builder.equal(root.get(COST), cost));
        }
        return this;
    }

    /**
     * Sets create date.
     *
     * @param createDate the create date
     * @return the create date
     */
    public OrderPredicateBuilder setCreateDate(LocalDateTime createDate) {
        if (createDate != null) {
            predicates.add(builder.equal(root.get(CREATE_DATE), createDate));
        }
        return this;
    }

    /**
     * Build list.
     *
     * @return the list
     */
    public List<Predicate> build() {
        return predicates;
    }
}
