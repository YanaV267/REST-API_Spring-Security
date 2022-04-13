package com.epam.esm.mapper;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.epam.esm.repository.ColumnName.USER_ID;

/**
 * The type User extractor.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Repository
public class UserExtractor implements ResultSetExtractor<List<User>> {
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;

    /**
     * Instantiates a new User extractor.
     *
     * @param userMapper  the user mapper
     * @param orderMapper the order mapper
     */
    @Autowired
    public UserExtractor(UserMapper userMapper, OrderMapper orderMapper) {
        this.userMapper = userMapper;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<User> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Long, User> users = new LinkedHashMap<>();
        while (resultSet.next()) {
            long userId = resultSet.getLong(USER_ID);
            User user;
            if (!users.containsKey(userId)) {
                user = userMapper.mapRow(resultSet, users.size());
                users.put(userId, user);
            } else {
                user = users.get(userId);
            }
            Order order = orderMapper.mapRow(resultSet, users.size() - 1);
            Set<Order> orders = user.getOrders();
            orders.add(order);
            user.setOrders(orders);
        }
        return new LinkedList<>(users.values());
    }
}