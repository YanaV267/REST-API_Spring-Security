package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Set;

public interface GiftCertificateRepository extends BaseRepository<GiftCertificate> {
    void update(GiftCertificate giftCertificate);

    Set<GiftCertificate> findBySeveralParameters(GiftCertificate certificate, String tagName,
                                                 List<String> sortTypes);
}
