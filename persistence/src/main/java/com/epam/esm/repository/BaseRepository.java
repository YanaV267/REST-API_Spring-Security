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
     * Create long.
     *
     * @param t the t
     * @return the long
     */
    long create(T t);

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(long id);

    /**
     * Find all set.
     *
     * @return the set
     */
    Set<T> findAll();

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<T> findById(long id);
}
