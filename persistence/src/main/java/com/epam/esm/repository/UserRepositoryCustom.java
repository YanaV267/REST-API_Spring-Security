package com.epam.esm.repository;

import com.epam.esm.entity.User;

import java.util.Set;

/**
 * The interface User repository custom.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface UserRepositoryCustom {

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

    /**
     * Gets last page.
     *
     * @return the last page
     */
    int getLastPage();
}
