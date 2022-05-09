package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Order repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Find by user id page.
     *
     * @param id       the id
     * @param pageable the pageable
     * @return the page
     */
    Page<Order> findByUser_Id(long id, Pageable pageable);
}
