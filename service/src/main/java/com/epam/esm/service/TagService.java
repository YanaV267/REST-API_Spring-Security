package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.Optional;
import java.util.Set;

/**
 * The interface Tag service.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface TagService extends BaseService<TagDto> {
    /**
     * Create boolean.
     *
     * @param tagDto the tag dto
     * @return the boolean
     */
    boolean create(TagDto tagDto);

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<TagDto> findByName(String name);

    /**
     * Find most used tag set.
     *
     * @param page the page
     * @return the set
     */
    Set<TagDto> findMostUsedTag(int page);
}
