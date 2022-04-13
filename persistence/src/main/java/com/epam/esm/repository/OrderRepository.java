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
     * @param userId the user id
     * @return the set
     */
    Set<Order> findAllOrdersByUser(long userId);

    /**
     * Find orders by several parameters set.
     *
     * @param order the order
     * @return the set
     */
    Set<Order> findOrdersBySeveralParameters(Order order);
}
