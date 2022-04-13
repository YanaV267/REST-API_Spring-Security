package com.epam.esm.mapper.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.util.CertificateDateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * The type Order mapper.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Service("orderMapper")
public class OrderMapper implements Mapper<Order, OrderDto> {
    private final UserMapper userMapper;
    private final CertificateDateFormatter dateFormatter;

    /**
     * Instantiates a new Order mapper.
     *
     * @param userMapper    the user mapper
     * @param dateFormatter the date formatter
     */
    @Autowired
    public OrderMapper(@Qualifier("userMapper") UserMapper userMapper, CertificateDateFormatter dateFormatter) {
        this.userMapper = userMapper;
        this.dateFormatter = dateFormatter;
    }

    @Override
    public OrderDto mapToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        UserDto userDto = userMapper.mapToDto(order.getUser());
        orderDto.setId(order.getId());
        orderDto.setUser(userDto);
        orderDto.setCertificate(order.getCertificate());
        orderDto.setCreateDate(order.getCreateDate());
        return orderDto;
    }

    @Override
    public Order mapToEntity(OrderDto orderDto) {
        Order order = new Order();
        User user = userMapper.mapToEntity(orderDto.getUser());
        order.setId(orderDto.getId());
        order.setUser(user);
        order.setCertificate(orderDto.getCertificate());
        LocalDateTime formattedDate = dateFormatter.format(orderDto.getCreateDate());
        order.setCreateDate(formattedDate);
        return order;
    }
}
