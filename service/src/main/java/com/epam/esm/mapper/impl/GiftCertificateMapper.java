package com.epam.esm.mapper.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component("certificateMapper")
public class GiftCertificateMapper implements Mapper<GiftCertificate, GiftCertificateDto> {
    @Override
    public GiftCertificateDto mapToDto(GiftCertificate giftCertificate) {
        GiftCertificateDto certificateDto = new GiftCertificateDto();
        certificateDto.setId(giftCertificate.getId());
        certificateDto.setName(giftCertificate.getName());
        certificateDto.setDescription(giftCertificate.getDescription());
        certificateDto.setDuration(giftCertificate.getDuration());
        certificateDto.setPrice(giftCertificate.getPrice());
        certificateDto.setCreateDate(giftCertificate.getCreateDate());
        certificateDto.setLastUpdateDate(giftCertificate.getLastUpdateDate());
        return certificateDto;
    }

    @Override
    public GiftCertificate mapToEntity(GiftCertificateDto certificateDto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(certificateDto.getId());
        giftCertificate.setName(certificateDto.getName());
        giftCertificate.setDescription(certificateDto.getDescription());
        giftCertificate.setDuration(certificateDto.getDuration());
        giftCertificate.setPrice(certificateDto.getPrice());
        giftCertificate.setCreateDate(certificateDto.getCreateDate());
        giftCertificate.setLastUpdateDate(certificateDto.getLastUpdateDate());
        return giftCertificate;
    }
}
