package com.epam.esm.builder;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Set;

import static com.epam.esm.repository.ColumnName.*;

/**
 * The type Order update builder.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public class OrderUpdateBuilder {
    private final Root<Order> root;
    private CriteriaUpdate<Order> query;

    /**
     * Instantiates a new Order update builder.
     *
     * @param query the query
     * @param root  the root
     */
    public OrderUpdateBuilder(CriteriaUpdate<Order> query, Root<Order> root) {
        this.query = query;
        this.root = root;
    }

    /**
     * Sets user.
     *
     * @param user the user
     * @return the user
     */
    public OrderUpdateBuilder setUser(User user) {
        if (user != null && user.getId() != 0) {
            query = query.set(root.get(USER).get(ID), user.getId());
        }
        return this;
    }

    /**
     * Sets certificates.
     *
     * @param certificates the certificates
     * @return the certificates
     */
    public OrderUpdateBuilder setCertificates(Set<GiftCertificate> certificates) {
        if (certificates != null) {
            for (GiftCertificate certificate : certificates) {
                query = query.set(root.get(CERTIFICATES).get(ID), certificate.getId());
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
    public OrderUpdateBuilder setCost(BigDecimal cost) {
        if (cost != null) {
            query = query.set(root.get(COST), cost);
        }
        return this;
    }

    /**
     * Build criteria update.
     *
     * @return the criteria update
     */
    public CriteriaUpdate<Order> build() {
        return query;
    }
}
