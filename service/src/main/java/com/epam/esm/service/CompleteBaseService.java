package com.epam.esm.service;

/**
 * The interface Complete base service.
 *
 * @param <T> the type parameter
 * @author YanaV
 * @project GiftCertificate
 */
public interface CompleteBaseService<T> extends BaseService<T> {
    /**
     * Create boolean.
     *
     * @param t the t
     * @return the boolean
     */
    boolean create(T t);

    /**
     * Update boolean.
     *
     * @param t the t
     * @return the boolean
     */
    boolean update(T t);
}
