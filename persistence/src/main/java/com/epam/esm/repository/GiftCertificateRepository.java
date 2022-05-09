package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Gift certificate repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>, GiftCertificateRepositoryCustom {

}
