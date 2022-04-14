package com.epam.esm.mapper.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.util.CertificateDateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * The type Order mapper.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Service("orderServiceMapper")
public class OrderMapper implements Mapper<Order, OrderDto> {
    private final CertificateDateFormatter dateFormatter;
    private final GiftCertificateMapper certificateMapper;

    /**
     * Instantiates a new Order mapper.
     *
     * @param dateFormatter     the date formatter
     * @param certificateMapper the certificate mapper
     */
    @Autowired
    public OrderMapper(CertificateDateFormatter dateFormatter, GiftCertificateMapper certificateMapper) {
        this.dateFormatter = dateFormatter;
        this.certificateMapper = certificateMapper;
    }

    @Override
    public OrderDto mapToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setCost(order.getCost());
        orderDto.setCreateDate(order.getCreateDate());
        GiftCertificateDto certificateDto = certificateMapper.mapToDto(order.getCertificate());
        orderDto.setCertificate(certificateDto);
        return orderDto;
    }

    @Override
    public Order mapToEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setCost(orderDto.getCost());
        LocalDateTime formattedDate = dateFormatter.format(orderDto.getCreateDate());
        order.setCreateDate(formattedDate);
        GiftCertificate certificate = certificateMapper.mapToEntity(orderDto.getCertificate());
        order.setCertificate(certificate);
        return order;
    }
}
