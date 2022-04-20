package com.epam.esm.repository;

import java.util.Optional;
import java.util.Set;

/**
 * The interface Base repository.
 *
 * @param <T> the type parameter
 * @author YanaV
 * @project GiftCertificate
 */
public interface BaseRepository<T> {
    /**
     * The constant MAX_RESULT_AMOUNT.
     */
    int MAX_RESULT_AMOUNT = 15;

    /**
     * Delete.
     *
     * @param t the t
     */
    void delete(T t);

    /**
     * Find all set.
     *
     * @param firstElementNumber the first element number
     * @return the set
     */
    Set<T> findAll(int firstElementNumber);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<T> findById(long id);

    /**
     * Gets last page.
     *
     * @return the last page
     */
    int getLastPage();
}
