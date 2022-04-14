package com.epam.esm.mapper.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type User mapper.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Service("userServiceMapper")
public class UserMapper implements Mapper<User, UserDto> {
    private final OrderMapper orderMapper;

    /**
     * Instantiates a new User mapper.
     *
     * @param orderMapper the order mapper
     */
    @Autowired
    public UserMapper(@Qualifier("orderServiceMapper") OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        userDto.setSurname(user.getSurname());
        userDto.setName(user.getName());
        userDto.setBalance(user.getBalance());
        Set<OrderDto> orders = user.getOrders().stream()
                .map(orderMapper::mapToDto)
                .collect(Collectors.toSet());
        userDto.setOrders(orders);
        return userDto;
    }

    @Override
    public User mapToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setLogin(userDto.getLogin());
        user.setSurname(userDto.getSurname());
        user.setName(userDto.getName());
        user.setBalance(userDto.getBalance());
        Set<Order> orders = userDto.getOrders().stream()
                .map(orderMapper::mapToEntity)
                .collect(Collectors.toSet());
        user.setOrders(orders);
        return user;
    }
}
