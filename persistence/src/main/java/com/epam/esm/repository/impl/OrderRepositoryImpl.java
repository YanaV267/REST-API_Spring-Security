package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
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
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<GiftCertificate> query = entityManager.getCriteriaBuilder()
                .createCriteriaUpdate(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        if (order.getUser().getId() != 0) {
            query = query.set(root.get(USER).get(ID),
                    String.valueOf(order.getUser().getId()));
        }
        if (order.getCertificate().getId() != 0) {
            query = query.set(root.get(CERTIFICATE).get(ID),
                    String.valueOf(order.getCertificate().getId()));
        }
        if (order.getCost() != null) {
            query = query.set(root.get(COST), order.getCost());
        }
        query.where(builder.equal(root.get(ID), order.getId()));
        entityManager.createQuery(query)
                .executeUpdate();
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
        Order order = entityManager.find(Order.class, id);
        return Optional.ofNullable(order);
    }

    @Override
    public Set<Order> findAllOrdersByUser(long userId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root)
                .where(builder.equal(root.join(USER).get(ID), userId));
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
            predicates.add(builder.like(root.get(USER).get(ID),
                    String.valueOf(order.getUser().getId())));
        }
        if (order.getCertificate().getId() != 0) {
            predicates.add(builder.like(root.get(CERTIFICATE).get(ID),
                    String.valueOf(order.getCertificate().getId())));
        }
        if (order.getCost() != null) {
            predicates.add(builder.equal(root.get(COST), order.getCost()));
        }
        if (order.getCreateDate() != null) {
            predicates.add(builder.equal(root.get(CREATE_DATE), order.getCreateDate()));
        }
        return predicates;
    }
}
