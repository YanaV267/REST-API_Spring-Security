package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GiftCertificateService extends BaseService<GiftCertificateDto> {
    boolean update(Map<String, Object> certificateData);

    Set<GiftCertificateDto> findBySeveralParameters(Map<String, Object> certificateData, List<String> sortTypes);
}
