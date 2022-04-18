package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;

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
     * @param page   the page
     * @param userId the user id
     * @return the set
     */
    Set<OrderDto> findAllByUser(int page, long userId);

    /**
     * Find by several parameters set.
     *
     * @param page     the page
     * @param orderDto the order dto
     * @return the set
     */
    Set<OrderDto> findBySeveralParameters(int page, OrderDto orderDto);
}
