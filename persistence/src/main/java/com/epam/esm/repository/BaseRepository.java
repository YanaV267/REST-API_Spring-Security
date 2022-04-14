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
     * Delete.
     *
     * @param t the t
     */
    void delete(T t);

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
