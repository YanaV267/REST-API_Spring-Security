package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Set;

/**
 * The interface Gift certificate repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface GiftCertificateRepository extends BaseRepository<GiftCertificate> {
    /**
     * Create long.
     *
     * @param certificate the certificate
     * @return the long
     */
    long create(GiftCertificate certificate);

    /**
     * Update.
     *
     * @param giftCertificate the gift certificate
     */
    void update(GiftCertificate giftCertificate);

    /**
     * Find by several parameters set.
     *
     * @param certificate the certificate
     * @param tags        the tag name
     * @param sortTypes   the sort types
     * @return the set
     */
    Set<GiftCertificate> findBySeveralParameters(GiftCertificate certificate, List<Tag> tags,
                                                 List<String> sortTypes);
}
