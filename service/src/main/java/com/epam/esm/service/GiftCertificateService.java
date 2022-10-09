package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;

import java.util.List;
import java.util.Set;

/**
 * The interface Gift certificate service.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface GiftCertificateService extends CompleteBaseService<GiftCertificateDto> {
    /**
     * Find all set.
     *
     * @return the set
     */
    Set<GiftCertificateDto> findAll();

    /**
     * Find by several parameters set.
     *
     * @param page           the page
     * @param certificateDto the certificate dto
     * @param tags           the tags
     * @param sortTypes      the sort types
     * @return the set
     */
    Set<GiftCertificateDto> findBySeveralParameters(int page, GiftCertificateDto certificateDto,
                                                    List<String> tags, List<String> sortTypes);
}
