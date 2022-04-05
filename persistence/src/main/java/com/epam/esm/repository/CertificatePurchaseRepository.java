package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;

/**
 * The interface Certificate purchase repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface CertificatePurchaseRepository {
    /**
     * Create boolean.
     *
     * @param certificate the certificate
     * @return the boolean
     */
    boolean create(GiftCertificate certificate);

    /**
     * Update boolean.
     *
     * @param certificate the certificate
     * @return the boolean
     */
    boolean update(GiftCertificate certificate);
}
