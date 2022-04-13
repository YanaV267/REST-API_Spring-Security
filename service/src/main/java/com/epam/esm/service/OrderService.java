package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;

import java.util.Map;
import java.util.Set;

/**
 * The interface Order service.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface OrderService extends CompleteBaseService<OrderDto> {
    /**
     * Find all by user set.
     *
     * @param userId the user id
     * @return the set
     */
    Set<OrderDto> findAllByUser(long userId);

    /**
     * Find by several parameters set.
     *
     * @param orderData the order data
     * @return the set
     */
    Set<OrderDto> findBySeveralParameters(Map<String, Object> orderData);
}
