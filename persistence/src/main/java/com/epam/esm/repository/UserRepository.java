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
     * @return the set
     */
    Set<User> findAllWithOrders();
}
