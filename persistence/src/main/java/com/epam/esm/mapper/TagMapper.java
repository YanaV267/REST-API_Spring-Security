package com.epam.esm.mapper;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.repository.ColumnName.ID;
import static com.epam.esm.repository.ColumnName.NAME;

public class TagMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Tag(resultSet.getLong(ID), resultSet.getString(NAME));
    }

}
