package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * The interface Order repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
}
