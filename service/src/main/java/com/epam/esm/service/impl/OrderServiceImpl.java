package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.impl.OrderMapper;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.CertificateDateFormatter;
import com.epam.esm.validator.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.repository.ColumnName.*;
import static com.epam.esm.util.ParameterName.*;

/**
 * @author YanaV
 * @project GiftCertificate
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final OrderValidator validator;
    private final OrderMapper mapper;
    private final CertificateDateFormatter dateFormatter;

    @Autowired
    public OrderServiceImpl(OrderRepository repository, OrderValidator validator,
                            @Qualifier("orderMapper") OrderMapper mapper,
                            CertificateDateFormatter dateFormatter) {
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
        this.dateFormatter = dateFormatter;
    }

    @Override
    public boolean create(Map<String, Object> orderData) {
        if (validator.checkAllOrderData(orderData)) {
            Order order = new Order.OrderBuilder()
                    .setUser(new User((Long) orderData.get(USER_ID)))
                    .setCertificate(new GiftCertificate((Long) orderData.get(GIFT_CERTIFICATE_ID)))
                    .setCost(new BigDecimal((String) orderData.get(ORDER_COST)))
                    .build();
            long id = repository.create(order);
            return id != 0;
        }
        return false;
    }

    @Override
    public boolean update(Map<String, Object> orderData) {
        if (orderData.containsKey(ID) && validator.checkId((String) orderData.get(ID))
                && validator.checkOrderData(orderData)) {
            Order order = retrieveOrderData(orderData);
            repository.update(order);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        if (repository.findById(id).isPresent()) {
            repository.delete(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Set<OrderDto> findAll() {
        Set<Order> orders = repository.findAll();
        return orders.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<OrderDto> findById(long id) {
        Optional<Order> order = repository.findById(id);
        if (order.isPresent()) {
            OrderDto orderDto = mapper.mapToDto(order.get());
            return Optional.of(orderDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Set<OrderDto> findAllByUser(long userId) {
        Set<Order> orders = repository.findAllOrdersByUser(userId);
        return orders.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<OrderDto> findBySeveralParameters(Map<String, Object> orderData) {
        if (validator.checkOrderData(orderData)) {
            Order order = retrieveOrderData(orderData);
            if (orderData.containsKey(LOGIN)) {
                order.getUser().setName((String) orderData.get(LOGIN));
            }
            if (orderData.containsKey(SURNAME)) {
                order.getUser().setName((String) orderData.get(SURNAME));
            }
            if (orderData.containsKey(NAME)) {
                order.getUser().setName((String) orderData.get(NAME));
            }
            Set<Order> orders = repository.findOrdersBySeveralParameters(order);
            return orders.stream()
                    .map(mapper::mapToDto)
                    .collect(Collectors.toSet());
        }
        return new LinkedHashSet<>();
    }

    private Order retrieveOrderData(Map<String, Object> orderData) {
        Order giftCertificate = new Order();
        if (orderData.containsKey(USER_ID)) {
            giftCertificate.setUser(new User((Long) orderData.get(USER_ID)));
        }
        if (orderData.containsKey(GIFT_CERTIFICATE_ID)) {
            giftCertificate.setCertificate(new GiftCertificate((Long) orderData.get(GIFT_CERTIFICATE_ID)));
        }
        if (orderData.containsKey(COST)) {
            giftCertificate.setCost(new BigDecimal((String) orderData.get(COST)));
        }
        if (orderData.containsKey(CREATE_DATE)) {
            LocalDateTime formattedDate = dateFormatter.format((String) orderData.get(CREATE_DATE));
            giftCertificate.setCreateDate(formattedDate);
        }
        return giftCertificate;
    }
}
