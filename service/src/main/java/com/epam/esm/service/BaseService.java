package com.epam.esm.service;

import java.util.Optional;
import java.util.Set;

/**
 * The interface Base service.
 *
 * @param <T> the type parameter
 * @author YanaV
 * @project GiftCertificate
 */
public interface BaseService<T> {
    /**
     * The constant MAX_RESULT_AMOUNT.
     */
    int MAX_RESULT_AMOUNT = 15;

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean delete(long id);

    /**
     * Find all set.
     *
     * @param page the page
     * @return the set
     */
    Set<T> findAll(int page);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<T> findById(long id);

    /**
     * Gets first element number.
     *
     * @param page the page
     * @return the first element number
     */
    default int getFirstElementNumber(int page) {
        return (page - 1) * MAX_RESULT_AMOUNT;
    }

    /**
     * Gets last page.
     *
     * @return the last page
     */
    int getLastPage();
}
