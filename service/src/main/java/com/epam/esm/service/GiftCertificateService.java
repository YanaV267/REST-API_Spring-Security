package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The interface Gift certificate service.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface GiftCertificateService extends BaseService<GiftCertificateDto> {
    /**
     * Update boolean.
     *
     * @param certificateData the certificate data
     * @return the boolean
     */
    boolean update(Map<String, Object> certificateData);

    /**
     * Find by several parameters set.
     *
     * @param certificateData the certificate data
     * @param sortTypes       the sort types
     * @return the set
     */
    Set<GiftCertificateDto> findBySeveralParameters(Map<String, Object> certificateData, List<String> sortTypes);
}
