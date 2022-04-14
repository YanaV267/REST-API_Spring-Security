package com.epam.esm.repository.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

import static com.epam.esm.repository.ColumnName.*;

/**
 * The type Order repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Repository
public class OrderRepositoryImpl implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long create(Order order) {
        entityManager.persist(order);
        return order.getId();
    }

    @Override
    public void update(Order order) {
        entityManager.merge(order);
    }

    @Override
    public void delete(Order order) {
        entityManager.remove(order);
    }

    @Override
    public Set<Order> findAll() {
        CriteriaQuery<Order> query = entityManager.getCriteriaBuilder()
                .createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root);
        return new LinkedHashSet<>(entityManager.createQuery(query)
                .getResultList());
    }

    @Override
    public Optional<Order> findById(long id) {
        try {
            Order order = entityManager.find(Order.class, id);
            return Optional.of(order);
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Set<Order> findAllOrdersByUser(long userId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = entityManager.getCriteriaBuilder()
                .createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        root.join(USER);
        query.select(root).where(builder.equal(root.get(USER).get(ID), userId));
        return new LinkedHashSet<>(entityManager.createQuery(query)
                .getResultList());
    }

    @Override
    public Set<Order> findOrdersBySeveralParameters(Order order) {
        CriteriaQuery<Order> query = entityManager.getCriteriaBuilder()
                .createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        root.join(USER);
        root.join(CERTIFICATE);
        Predicate[] predicates = createPredicates(root, order)
                .toArray(new Predicate[0]);
        query.select(root)
                .where(predicates);
        return new LinkedHashSet<>(entityManager.createQuery(query)
                .getResultList());
    }

    private List<Predicate> createPredicates(Root<Order> root, Order order) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();
        if (order.getUser().getId() != 0) {
            predicates.add(builder.like(root.get(USER).get(NAME),
                    String.valueOf(order.getUser().getId())));
        }
        if (order.getCertificate().getId() != 0) {
            predicates.add(builder.like(root.get(CERTIFICATE).get(NAME),
                    String.valueOf(order.getCertificate().getId())));
        }
        if (order.getCost() != null) {
            predicates.add(builder.like(root.get(COST), String.valueOf(order.getCost())));
        }
        if (order.getCreateDate() != null) {
            predicates.add(builder.equal(root.get(CREATE_DATE), String.valueOf(order.getCreateDate())));
        }
        return predicates;
    }
}
