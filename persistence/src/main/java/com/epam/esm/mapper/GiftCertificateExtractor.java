package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.epam.esm.repository.ColumnName.GIFT_CERTIFICATE_ID;

public class GiftCertificateExtractor implements ResultSetExtractor<List<GiftCertificate>> {
    @Override
    public List<GiftCertificate> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Long, GiftCertificate> certificates = new LinkedHashMap<>();
        GiftCertificateMapper certificateMapper = new GiftCertificateMapper();
        TagMapper tagMapper = new TagMapper();
        while (resultSet.next()) {
            long giftCertificateId = resultSet.getLong(GIFT_CERTIFICATE_ID);
            GiftCertificate giftCertificate;
            if (!certificates.containsKey(giftCertificateId)) {
                giftCertificate = certificateMapper.mapRow(resultSet, certificates.size());
                certificates.put(giftCertificateId, giftCertificate);
            } else {
                giftCertificate = certificates.get(giftCertificateId);
            }
            Tag tag = tagMapper.mapRow(resultSet, certificates.size() - 1);
            Set<Tag> tags = giftCertificate.getTags();
            tags.add(tag);
            giftCertificate.setTags(tags);
        }
        return new LinkedList<>(certificates.values());
    }
}