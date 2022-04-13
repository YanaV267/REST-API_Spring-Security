package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.repository.ColumnName.*;

/**
 * The type Gift certificate mapper.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Repository
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new GiftCertificate.GiftCertificateBuilder()
                .setId(resultSet.getLong(GIFT_CERTIFICATE_ID))
                .setName(resultSet.getString(GIFT_CERTIFICATE_NAME))
                .setDescription(resultSet.getString(GIFT_CERTIFICATE_DESCRIPTION))
                .setPrice(resultSet.getBigDecimal(GIFT_CERTIFICATE_PRICE))
                .setDuration(resultSet.getInt(GIFT_CERTIFICATE_DURATION))
                .setCreateDate(resultSet.getTimestamp(GIFT_CERTIFICATE_CREATE_DATE).toLocalDateTime())
                .setLastUpdateDate(resultSet.getTimestamp(GIFT_CERTIFICATE_LAST_UPDATE_DATE).toLocalDateTime())
                .build();
    }

}
