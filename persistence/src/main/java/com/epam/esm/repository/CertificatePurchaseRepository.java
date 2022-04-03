package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;

public interface CertificatePurchaseRepository {
    boolean create(GiftCertificate certificate);

    boolean update(GiftCertificate certificate);
}
