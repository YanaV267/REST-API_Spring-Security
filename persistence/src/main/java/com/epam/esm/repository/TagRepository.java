package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Tag repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<Tag> findByName(String name);

    /**
     * Find most used tag page.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Query(value = "SELECT id, name FROM (SELECT max(amount), id, name FROM " +
            "(SELECT o.id_user, count(*) AS amount, t.id AS id, t.name as name FROM tags t " +
            "JOIN tag_reference cp ON t.id = cp.id_tag " +
            "JOIN gift_certificates gc ON gc.id = cp.id_certificate " +
            "JOIN certificate_purchase op ON op.id_certificate = gc.id " +
            "JOIN orders o ON op.id_order = o.id " +
            "GROUP BY id_user, t.name ORDER BY amount DESC) AS tag_amount " +
            "WHERE id_user = " +
            "(SELECT id_user FROM " +
            "(SELECT id_user, max(summary) FROM " +
            "(SELECT id_user, sum(cost) AS summary FROM orders " +
            "GROUP BY id_user) AS order_sum) " +
            "AS max_sum) " +
            "GROUP BY id_user, name) AS most_used", nativeQuery = true)
    Page<Tag> findMostUsedTag(Pageable pageable);
}
