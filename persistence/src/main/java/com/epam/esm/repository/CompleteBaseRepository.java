package com.epam.esm.repository;

/**
 * The interface Complete base repository.
 *
 * @param <T> the type parameter
 * @author YanaV
 * @project GiftCertificate
 */
public interface CompleteBaseRepository<T> extends BaseRepository<T> {
    /**
     * Create long.
     *
     * @param t the t
     * @return the long
     */
    long create(T t);

    /**
     * Update.
     *
     * @param t the t
     */
    void update(T t);
}
