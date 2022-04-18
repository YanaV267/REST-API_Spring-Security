package com.epam.esm.repository;

import com.epam.esm.entity.User;

import java.util.Set;

/**
 * The interface User repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface UserRepository extends BaseRepository<User> {
    /**
     * Find all with orders set.
     *
     * @param firstElementNumber the first element number
     * @return the set
     */
    Set<User> findAllWithOrders(int firstElementNumber);

    /**
     * Find with highest order cost set.
     *
     * @param firstElementNumber the first element number
     * @return the set
     */
    Set<User> findWithHighestOrderCost(int firstElementNumber);
}
