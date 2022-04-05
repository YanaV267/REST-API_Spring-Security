package com.epam.esm.mapper;

import org.springframework.stereotype.Component;

/**
 * The interface Mapper.
 *
 * @param <T> the type parameter
 * @param <D> the type parameter
 */
@Component
/**
 * @author YanaV
 * @project GiftCertificate
 */
public interface Mapper<T, D> {
    /**
     * Map to dto d.
     *
     * @param t the t
     * @return the d
     */
    D mapToDto(T t);

    /**
     * Map to entity t.
     *
     * @param d the d
     * @return the t
     */
    T mapToEntity(D d);
}
