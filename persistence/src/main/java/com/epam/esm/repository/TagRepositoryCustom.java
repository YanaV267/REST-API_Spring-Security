package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.Set;

/**
 * The interface Tag repository custom.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface TagRepositoryCustom {
    /**
     * Find most used tag set.
     *
     * @param firstElementNumber the first element number
     * @return the set
     */
    Set<Tag> findMostUsedTag(int firstElementNumber);

    /**
     * Gets last page.
     *
     * @return the last page
     */
    int getLastPage();
}
