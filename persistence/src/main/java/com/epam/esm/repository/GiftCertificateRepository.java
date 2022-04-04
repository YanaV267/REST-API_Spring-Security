package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;

import java.util.Set;

public interface GiftCertificateRepository extends BaseRepository<GiftCertificate> {
    boolean update(GiftCertificate giftCertificate);

    Set<GiftCertificate> findByName(String name);

    Set<GiftCertificate> findByDescription(String description);
}
