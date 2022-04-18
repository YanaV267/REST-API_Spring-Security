package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.Optional;
import java.util.Set;

/**
 * The interface Tag repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface TagRepository extends BaseRepository<Tag> {
    /**
     * Create long.
     *
     * @param tag the tag
     * @return the long
     */
    long create(Tag tag);

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<Tag> findByName(String name);

    /**
     * Find most used tag set.
     *
     * @param firstElementNumber the first element number
     * @return the set
     */
    Set<Tag> findMostUsedTag(int firstElementNumber);
}
