package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Set;

/**
 * The interface Gift certificate repository custom.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface GiftCertificateRepositoryCustom {
    /**
     * Update.
     *
     * @param certificate the certificate
     */
    void update(GiftCertificate certificate);

    /**
     * Find by several parameters set.
     *
     * @param firstElementNumber the first element number
     * @param certificate        the certificate
     * @param sortTypes          the sort types
     * @return the set
     */
    Set<GiftCertificate> findBySeveralParameters(int firstElementNumber, GiftCertificate certificate,
                                                 List<String> sortTypes);

    /**
     * Gets last page.
     *
     * @return the last page
     */
    int getLastPage();
}
