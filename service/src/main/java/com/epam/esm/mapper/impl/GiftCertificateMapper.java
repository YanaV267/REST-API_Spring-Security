package com.epam.esm.mapper.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.util.CertificateDateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Gift certificate mapper.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Component("certificateMapper")
public class GiftCertificateMapper implements Mapper<GiftCertificate, GiftCertificateDto> {
    private final TagMapper tagMapper;
    private final CertificateDateFormatter dateFormatter;

    /**
     * Instantiates a new Gift certificate mapper.
     *
     * @param tagMapper     the tag mapper
     * @param dateFormatter the date formatter
     */
    @Autowired
    public GiftCertificateMapper(@Qualifier("tagMapper") TagMapper tagMapper, CertificateDateFormatter dateFormatter) {
        this.tagMapper = tagMapper;
        this.dateFormatter = dateFormatter;
    }

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
        Set<TagDto> tags = giftCertificate.getTags().stream()
                .map(tagMapper::mapToDto)
                .collect(Collectors.toSet());
        certificateDto.setTags(tags);
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
        LocalDateTime formattedDate = dateFormatter.format(certificateDto.getCreateDate());
        giftCertificate.setCreateDate(formattedDate);
        formattedDate = dateFormatter.format(certificateDto.getLastUpdateDate());
        giftCertificate.setLastUpdateDate(formattedDate);
        Set<Tag> tags = certificateDto.getTags().stream()
                .map(tagMapper::mapToEntity)
                .collect(Collectors.toSet());
        giftCertificate.setTags(tags);
        return giftCertificate;
    }
}
