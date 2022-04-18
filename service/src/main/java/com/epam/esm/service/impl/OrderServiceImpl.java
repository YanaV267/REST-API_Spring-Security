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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final OrderMapper mapper;

    /**
     * Instantiates a new Order service.
     *
     * @param repository            the repository
     * @param certificateRepository the certificate repository
     * @param userRepository        the user repository
     * @param mapper                the mapper
     */
    @Autowired
    public OrderServiceImpl(OrderRepository repository,
                            GiftCertificateRepository certificateRepository,
                            UserRepository userRepository,
                            @Qualifier("orderServiceMapper") OrderMapper mapper) {
        this.repository = repository;
        this.certificateRepository = certificateRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public boolean create(OrderDto orderDto) {
        long certificateId = orderDto.getCertificateDto().getId();
        long userId = orderDto.getUserDto().getId();
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
        return false;
    }

    @Override
    public boolean update(OrderDto orderDto) {
        Order order = mapper.mapToEntity(orderDto);
        repository.update(order);
        return true;
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
        Set<Order> orders = repository.findAllByUser(firstElementNumber, userId);
        return orders.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<OrderDto> findBySeveralParameters(int page, OrderDto orderDto) {
        Order order = mapper.mapToEntity(orderDto);
        int firstElementNumber = getFirstElementNumber(page);
        Set<Order> orders = repository.findBySeveralParameters(firstElementNumber, order);
        return orders.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toSet());
    }
}