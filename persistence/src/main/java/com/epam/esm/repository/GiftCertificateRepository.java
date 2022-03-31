package com.epam.esm.repository;

import com.epam.esm.repository.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateRepository extends MainRepository<GiftCertificate> {
    boolean update(GiftCertificate giftCertificate);

    List<GiftCertificate> findByName(String name);

    List<GiftCertificate> findByDescription(String description);
}
