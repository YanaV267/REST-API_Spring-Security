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
}
