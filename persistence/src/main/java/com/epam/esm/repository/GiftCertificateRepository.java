package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;

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
     * Update.
     *
     * @param giftCertificate the gift certificate
     */
    void update(GiftCertificate giftCertificate);

    /**
     * Find by several parameters set.
     *
     * @param certificate the certificate
     * @param tagName     the tag name
     * @param sortTypes   the sort types
     * @return the set
     */
    Set<GiftCertificate> findBySeveralParameters(GiftCertificate certificate, String tagName,
                                                 List<String> sortTypes);
}
