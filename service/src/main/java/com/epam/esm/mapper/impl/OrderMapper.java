package com.epam.esm.mapper.impl;

import com.epam.esm.dto.GiftCertificatePurchaseDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificatePurchase;
import com.epam.esm.entity.Order;
import com.epam.esm.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Order mapper.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Service("orderServiceMapper")
public class OrderMapper implements Mapper<Order, OrderDto> {
    private final GiftCertificateMapper certificateMapper;

    /**
     * Instantiates a new Order mapper.
     *
     * @param certificateMapper the certificate mapper
     */
    @Autowired
    public OrderMapper(GiftCertificateMapper certificateMapper) {
        this.certificateMapper = certificateMapper;
    }

    @Override
    public OrderDto mapToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setCost(order.getCost());
        orderDto.setCreateDate(order.getCreateDate());
        Set<GiftCertificatePurchaseDto> certificates = order.getCertificates()
                .stream()
                .map(c -> new GiftCertificatePurchaseDto(certificateMapper.mapToDto(c.getCertificate()), c.getAmount()))
                .collect(Collectors.toSet());
        orderDto.setCertificates(certificates);
        return orderDto;
    }

    @Override
    public Order mapToEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setCost(orderDto.getCost());
        order.setCreateDate(orderDto.getCreateDate());
        Set<GiftCertificatePurchase> certificates = orderDto.getCertificates()
                .stream()
                .map(c -> new GiftCertificatePurchase(certificateMapper.mapToEntity(c.getCertificate()), c.getAmount()))
                .collect(Collectors.toSet());
        order.setCertificates(certificates);
        return order;
    }
}
