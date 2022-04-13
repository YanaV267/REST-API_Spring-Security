package com.epam.esm.service;

import java.util.Map;

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
     * @param data the data
     * @return the boolean
     */
    boolean create(Map<String, Object> data);

    /**
     * Update boolean.
     *
     * @param data the data
     * @return the boolean
     */
    boolean update(Map<String, Object> data);
}
