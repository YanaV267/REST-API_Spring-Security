package com.epam.esm.mapper;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.repository.ColumnName.*;

/**
 * The type Tag mapper.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Repository
public class TagMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Tag(resultSet.getLong(TAG_ID), resultSet.getString(TAG_NAME));
    }
}
