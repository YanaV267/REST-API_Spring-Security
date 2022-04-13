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
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean delete(long id);

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
