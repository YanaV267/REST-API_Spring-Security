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
    private int lastPage;
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
        CriteriaUpdate<Order> query = entityManager.getCriteriaBuilder()
                .createCriteriaUpdate(Order.class);
        Root<Order> root = query.from(Order.class);
        if (order.getUser().getId() != 0) {
            query = query.set(root.get(USER).get(ID), order.getUser().getId());
        }
        for (GiftCertificate certificate : order.getCertificates()) {
            query = query.set(root.get(CERTIFICATES).get(ID), certificate.getId());
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
    public Set<Order> findAll(int firstElementNumber) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        CriteriaQuery<Long> pageQuery = builder.createQuery(Long.class);

        query.select(query.from(Order.class));
        pageQuery.select(builder.count(pageQuery.from(Order.class)));

        long amount = entityManager.createQuery(pageQuery).getSingleResult();
        lastPage = (int) Math.ceil((double) amount / MAX_RESULT_AMOUNT);
        return new LinkedHashSet<>(entityManager.createQuery(query)
                .setFirstResult(firstElementNumber)
                .setMaxResults(MAX_RESULT_AMOUNT)
                .getResultList());
    }

    @Override
    public Optional<Order> findById(long id) {
        Order order = entityManager.find(Order.class, id);
        return Optional.ofNullable(order);
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
        lastPage = (int) Math.ceil((double) amount / MAX_RESULT_AMOUNT);
        return new LinkedHashSet<>(entityManager.createQuery(query)
                .setFirstResult(firstElementNumber)
                .setMaxResults(MAX_RESULT_AMOUNT)
                .getResultList());
    }

    @Override
    public Set<Order> findBySeveralParameters(int firstElementNumber, Order order, List<Integer> userIds) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        CriteriaQuery<Long> pageQuery = builder.createQuery(Long.class);
        Root<Order> root = query.from(Order.class);
        Root<Order> pageRoot = pageQuery.from(Order.class);

        Predicate[] predicates = createPredicates(root, order, userIds).toArray(new Predicate[0]);
        Predicate[] pagePredicates = createPredicates(pageRoot, order, userIds).toArray(new Predicate[0]);
        query.select(root).where(predicates);
        pageQuery.select(builder.count(pageRoot)).where(pagePredicates);

        long amount = entityManager.createQuery(pageQuery)
                .getSingleResult();
        lastPage = (int) Math.ceil((double) amount / MAX_RESULT_AMOUNT);
        return new LinkedHashSet<>(entityManager.createQuery(query)
                .setFirstResult(firstElementNumber)
                .setMaxResults(MAX_RESULT_AMOUNT)
                .getResultList());
    }

    private List<Predicate> createPredicates(Root<Order> root, Order order, List<Integer> userIds) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();
        if (order.getCertificates() != null) {
            for (GiftCertificate certificate : order.getCertificates()) {
                predicates.add(builder.equal(root.join(CERTIFICATES).get(ID), certificate.getId()));
            }
        }
        if (userIds != null) {
            for (Integer userId : userIds) {
                predicates.add(builder.equal(root.join(USER).get(ID), userId));
            }
        }
        if (order.getCost() != null) {
            predicates.add(builder.equal(root.get(COST), order.getCost()));
        }
        if (order.getCreateDate() != null) {
            predicates.add(builder.equal(root.get(CREATE_DATE), order.getCreateDate()));
        }
        return predicates;
    }

    @Override
    public int getLastPage() {
        return lastPage;
    }
}
