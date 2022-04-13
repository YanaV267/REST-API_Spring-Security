package com.epam.esm.mapper;

import com.epam.esm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Order extractor.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Repository
public class OrderExtractor implements ResultSetExtractor<List<Order>> {
    private final OrderMapper orderMapper;

    /**
     * Instantiates a new Order extractor.
     *
     * @param orderMapper the order mapper
     */
    @Autowired
    public OrderExtractor(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public List<Order> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Order> orders = new LinkedList<>();
        while (resultSet.next()) {
            Order order = orderMapper.mapRow(resultSet, orders.size());
            orders.add(order);
        }
        return orders;
    }
}