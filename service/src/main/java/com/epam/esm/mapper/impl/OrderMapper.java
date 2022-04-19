package com.epam.esm.mapper.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (order.getCertificate() != null) {
            GiftCertificateDto certificateDto = certificateMapper.mapToDto(order.getCertificate());
            orderDto.setCertificate(certificateDto);
        }
        return orderDto;
    }

    @Override
    public Order mapToEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setCost(orderDto.getCost());
        order.setCreateDate(orderDto.getCreateDate());
        GiftCertificate certificate = certificateMapper.mapToEntity(orderDto.getCertificate());
        order.setCertificate(certificate);
        return order;
    }
}
