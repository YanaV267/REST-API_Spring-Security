package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateRepository extends BaseRepository<GiftCertificate> {
    boolean update(GiftCertificate giftCertificate);

    List<GiftCertificate> findByName(String name);

    List<GiftCertificate> findByDescription(String description);
}
