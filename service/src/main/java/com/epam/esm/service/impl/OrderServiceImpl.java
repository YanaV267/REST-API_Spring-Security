package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.impl.OrderMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.CertificateDateFormatter;
import com.epam.esm.validator.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.util.ParameterName.*;

/**
 * The type Order service.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final GiftCertificateRepository certificateRepository;
    private final UserRepository userRepository;
    private final OrderValidator validator;
    private final OrderMapper mapper;
    private final CertificateDateFormatter dateFormatter;

    /**
     * Instantiates a new Order service.
     *
     * @param repository            the repository
     * @param validator             the validator
     * @param certificateRepository the certificate repository
     * @param userRepository        the user repository
     * @param mapper                the mapper
     * @param dateFormatter         the date formatter
     */
    @Autowired
    public OrderServiceImpl(OrderRepository repository, OrderValidator validator,
                            GiftCertificateRepository certificateRepository,
                            UserRepository userRepository,
                            @Qualifier("orderServiceMapper") OrderMapper mapper,
                            CertificateDateFormatter dateFormatter) {
        this.repository = repository;
        this.certificateRepository = certificateRepository;
        this.userRepository = userRepository;
        this.validator = validator;
        this.mapper = mapper;
        this.dateFormatter = dateFormatter;
    }

    @Override
    @Transactional
    public boolean create(Map<String, Object> orderData) {
        if (validator.checkAllOrderData(orderData)) {
            long certificateId = Long.parseLong((String) orderData.get(ID_CERTIFICATE));
            long userId = Long.parseLong((String) orderData.get(ID_USER));
            Optional<GiftCertificate> certificate = certificateRepository.findById(certificateId);
            Optional<User> user = userRepository.findById(userId);
            if (certificate.isPresent() && user.isPresent()) {
                Order order = Order.builder()
                        .user(new User(userId))
                        .certificate(new GiftCertificate(certificateId))
                        .cost(certificate.get().getPrice())
                        .build();
                repository.create(order);
                return true;
            }
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
    @Transactional
    public boolean delete(long id) {
        Optional<Order> order = repository.findById(id);
        if (order.isPresent()) {
            repository.delete(order.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Set<OrderDto> findAll(int page) {
        int firstElementNumber = getFirstElementNumber(page);
        Set<Order> orders = repository.findAll(firstElementNumber);
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
    public Set<OrderDto> findAllByUser(int page, long userId) {
        int firstElementNumber = getFirstElementNumber(page);
        Set<Order> orders = repository.findAllOrdersByUser(firstElementNumber, userId);
        return orders.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<OrderDto> findBySeveralParameters(int page, Map<String, Object> orderData) {
        if (validator.checkOrderData(orderData)) {
            Order order = retrieveOrderData(orderData);
            int firstElementNumber = getFirstElementNumber(page);
            Set<Order> orders = repository.findOrdersBySeveralParameters(firstElementNumber, order);
            return orders.stream()
                    .map(mapper::mapToDto)
                    .collect(Collectors.toSet());
        }
        return new LinkedHashSet<>();
    }

    private Order retrieveOrderData(Map<String, Object> orderData) {
        Order giftCertificate = new Order();
        if (orderData.containsKey(ID_USER)) {
            giftCertificate.setUser(new User(Long.parseLong((String) orderData.get(ID_USER))));
        }
        if (orderData.containsKey(ID_CERTIFICATE)) {
            giftCertificate.setCertificate(new GiftCertificate(Long.parseLong((String) orderData.get(ID_CERTIFICATE))));
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
