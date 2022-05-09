package com.epam.esm.repository.impl;

import com.epam.esm.builder.OrderPredicateBuilder;
import com.epam.esm.builder.OrderUpdateBuilder;
import com.epam.esm.entity.Order;
import com.epam.esm.repository.OrderRepositoryCustom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.epam.esm.repository.ColumnName.ID;
import static com.epam.esm.repository.ColumnName.USER;

/**
 * The type Order repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Repository
public class OrderRepositoryImpl implements OrderRepositoryCustom {
    @Value("${max.result.amount}")
    private int maxResultAmount;
    private int lastPage;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void update(Order order) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Order> query = entityManager.getCriteriaBuilder()
                .createCriteriaUpdate(Order.class);
        Root<Order> root = query.from(Order.class);
        query = new OrderUpdateBuilder(query, root)
                .setUser(order.getUser())
                .setCertificates(order.getCertificates())
                .setCost(order.getCost())
                .build();
        query.where(builder.equal(root.get(ID), order.getId()));
        entityManager.createQuery(query)
                .executeUpdate();
    }

    @Override
    public Set<Order> findAllByUser(int firstElementNumber, long userId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        CriteriaQuery<Long> pageQuery = builder.createQuery(Long.class);
        Root<Order> root = query.from(Order.class);
        Root<Order> pageRoot = pageQuery.from(Order.class);

        query.select(root)
                .where(builder.equal(root.join(USER).get(ID), userId));
        pageQuery.select(builder.count(pageRoot))
                .where(builder.equal(pageRoot.join(USER).get(ID), userId));

        long amount = entityManager.createQuery(pageQuery).getSingleResult();
        lastPage = (int) Math.ceil((double) amount / maxResultAmount);
        return new LinkedHashSet<>(entityManager.createQuery(query)
                .setFirstResult(firstElementNumber)
                .setMaxResults(maxResultAmount)
                .getResultList());
    }

    @Override
    public Set<Order> findBySeveralParameters(int firstElementNumber, Order order, List<Integer> userIds) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        CriteriaQuery<Long> pageQuery = builder.createQuery(Long.class);
        Root<Order> root = query.from(Order.class);
        Root<Order> pageRoot = pageQuery.from(Order.class);

        Predicate[] predicates = new OrderPredicateBuilder(builder, root)
                .setUser(userIds)
                .setCertificates(order.getCertificates())
                .setCost(order.getCost())
                .setCreateDate(order.getCreateDate())
                .build()
                .toArray(new Predicate[0]);
        Predicate[] pagePredicates = new OrderPredicateBuilder(builder, pageRoot)
                .setUser(userIds)
                .setCertificates(order.getCertificates())
                .setCost(order.getCost())
                .setCreateDate(order.getCreateDate())
                .build()
                .toArray(new Predicate[0]);
        query.select(root).where(predicates);
        pageQuery.select(builder.count(pageRoot)).where(pagePredicates);

        long amount = entityManager.createQuery(pageQuery)
                .getSingleResult();
        lastPage = (int) Math.ceil((double) amount / maxResultAmount);
        return new LinkedHashSet<>(entityManager.createQuery(query)
                .setFirstResult(firstElementNumber)
                .setMaxResults(maxResultAmount)
                .getResultList());
    }

    @Override
    public int getLastPage() {
        return lastPage;
    }
}
