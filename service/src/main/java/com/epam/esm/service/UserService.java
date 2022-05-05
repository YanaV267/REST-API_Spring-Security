package com.epam.esm.service;

import com.epam.esm.dto.UserDto;

import java.util.Set;

/**
 * The interface User service.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface UserService extends BaseService<UserDto> {
    /**
     * Create boolean.
     *
     * @param userDto the userDto
     * @return the boolean
     */
    boolean create(UserDto userDto);

    /**
     * Find all with orders set.
     *
     * @param page the page
     * @return the set
     */
    Set<UserDto> findAllWithOrders(int page);

    /**
     * Find with highest order cost set.
     *
     * @param page the page
     * @return the set
     */
    Set<UserDto> findWithHighestOrderCost(int page);

    /**
     * Find with highest order cost most used tag set.
     *
     * @param page the page
     * @return the set
     */
    Set<UserDto> findWithHighestOrderCostMostUsedTag(int page);
}
