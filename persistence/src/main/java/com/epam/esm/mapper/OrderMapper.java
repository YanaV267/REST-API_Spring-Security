package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.repository.ColumnName.*;

/**
 * The type Order mapper.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Repository
public class OrderMapper implements RowMapper<Order> {
    private final UserMapper userMapper;
    private final GiftCertificateMapper certificateMapper;

    /**
     * Instantiates a new Order mapper.
     *
     * @param userMapper        the user mapper
     * @param certificateMapper the certificate mapper
     */
    @Autowired
    public OrderMapper(UserMapper userMapper, GiftCertificateMapper certificateMapper) {
        this.userMapper = userMapper;
        this.certificateMapper = certificateMapper;
    }

    @Override
    public Order mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Order order = new Order();
        GiftCertificate certificate = certificateMapper.mapRow(resultSet, rowNum);
        User user = userMapper.mapRow(resultSet, rowNum);
        order.setId(resultSet.getLong(ORDER_ID));
        order.setCertificate(certificate);
        order.setUser(user);
        order.setCost(resultSet.getBigDecimal(ORDER_COST));
        order.setCreateDate(resultSet.getTimestamp(ORDER_CREATE_DATE).toLocalDateTime());
        return order;
    }
}
