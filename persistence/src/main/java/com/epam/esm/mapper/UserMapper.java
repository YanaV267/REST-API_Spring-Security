package com.epam.esm.mapper;

import com.epam.esm.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.repository.ColumnName.*;

/**
 * The type User mapper.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Repository
public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(USER_ID));
        user.setLogin(resultSet.getString(USER_LOGIN));
        user.setSurname(resultSet.getString(USER_SURNAME));
        user.setName(resultSet.getString(USER_NAME));
        user.setBalance(resultSet.getBigDecimal(USER_BALANCE));
        return user;
    }
}
