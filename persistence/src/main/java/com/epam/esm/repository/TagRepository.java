package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface Tag repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface TagRepository extends JpaRepository<Tag, Long>, TagRepositoryCustom {
    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<Tag> findByName(String name);
}
