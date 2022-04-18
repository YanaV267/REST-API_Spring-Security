package com.epam.esm.repository;

import com.epam.esm.entity.Order;

import java.util.Set;

/**
 * The interface Order repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface OrderRepository extends CompleteBaseRepository<Order> {
    /**
     * Find all orders by user set.
     *
     * @param firstElementNumber the first element number
     * @param userId             the user id
     * @return the set
     */
    Set<Order> findAllOrdersByUser(int firstElementNumber, long userId);

    /**
     * Find orders by several parameters set.
     *
     * @param firstElementNumber the first element number
     * @param order              the order
     * @return the set
     */
    Set<Order> findOrdersBySeveralParameters(int firstElementNumber, Order order);
}
