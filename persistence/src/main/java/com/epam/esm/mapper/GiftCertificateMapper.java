package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.repository.ColumnName.*;

public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new GiftCertificate.GiftCertificateBuilder()
                .setId(resultSet.getLong(ID))
                .setName(resultSet.getString(NAME))
                .setDescription(resultSet.getString(DESCRIPTION))
                .setPrice(resultSet.getBigDecimal(PRICE))
                .setDuration(resultSet.getInt(DURATION))
                .setCreateDate(resultSet.getTimestamp(CREATE_DATE).toLocalDateTime())
                .setLastUpdateDate(resultSet.getTimestamp(LAST_UPDATE_DATE).toLocalDateTime())
                .build();
    }

}
