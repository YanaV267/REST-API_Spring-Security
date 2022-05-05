package com.epam.esm.repository;

import com.epam.esm.entity.User;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

/**
 * The interface User repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface UserRepository extends BaseRepository<User> {
    /**
     * Create boolean.
     *
     * @param user the user
     * @return the long
     */
    long create(User user);

    /**
     * Update balance.
     *
     * @param userId     the user id
     * @param newBalance the new balance
     */
    void updateBalance(long userId, BigDecimal newBalance);

    /**
     * Find with highest order cost set.
     *
     * @param firstElementNumber the first element number
     * @return the set
     */
    Set<User> findWithHighestOrderCost(int firstElementNumber);

    /**
     * Find with highest order cost most used tag set.
     *
     * @param firstElementNumber the first element number
     * @return the set
     */
    Set<User> findWithHighestOrderCostMostUsedTag(int firstElementNumber);

    Optional<User> findUserByLogin(String login);
}
