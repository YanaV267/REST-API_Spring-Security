package com.epam.esm.repository;

import com.epam.esm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * The interface User repository.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Update balance.
     *
     * @param balance the balance
     * @param id      the id
     */
    @Modifying
    @Query(value = "UPDATE User u SET u.balance = :balance WHERE u.id = :id")
    void updateBalance(@Param(value = "balance") BigDecimal balance, @Param(value = "id") long id);

    /**
     * Find by login optional.
     *
     * @param login the login
     * @return the optional
     */
    Optional<User> findByLogin(String login);

    /**
     * Find with highest order cost page.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Query(value = "SELECT id, login, surname, name, balance, max_summary FROM " +
            "(SELECT id, login, surname, name, balance, max(summary) AS max_summary FROM " +
            "(SELECT users.id AS id, login, surname, name, balance, sum(cost) AS summary FROM orders " +
            "JOIN users ON orders.id_user = users.id " +
            "GROUP BY id_user ORDER BY summary DESC) AS order_sum " +
            "ORDER BY max_summary DESC) AS max_sum", nativeQuery = true)
    Page<User> findByHighestOrderCost(Pageable pageable);

    /**
     * Find with highest order cost most used tag page.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Query(value = "SELECT id, login, surname, name, balance FROM " +
            "(SELECT id_user as id, login, surname, name, balance, id_tag, max(summary) " +
            "FROM (SELECT id_user, login, surname, u.name as name, balance, t.id as id_tag, sum(cost) AS summary " +
            "FROM orders " +
            "JOIN certificate_purchase p on orders.id = p.id_order " +
            "JOIN users u on u.id = orders.id_user " +
            "JOIN gift_certificates g on g.id = p.id_certificate " +
            "JOIN tag_reference c on g.id = c.id_certificate " +
            "JOIN tags t on t.id = c.id_tag " +
            "GROUP BY id_user) AS order_sum " +
            "WHERE id_tag = " +
            "(SELECT id " +
            "FROM (SELECT max(amount), id " +
            "FROM (SELECT count(*) AS amount, t.id AS id " +
            "FROM tags t " +
            "JOIN tag_reference cp ON t.id = cp.id_tag " +
            "JOIN gift_certificates gc ON gc.id = cp.id_certificate " +
            "JOIN certificate_purchase op ON op.id_certificate = gc.id " +
            "JOIN orders o ON op.id_order = o.id " +
            "GROUP BY t.name " +
            "ORDER BY amount DESC) AS tag_amount) " +
            "AS max_amount) " +
            "GROUP BY id_tag) AS most_used", nativeQuery = true)
    Page<User> findByHighestOrderCostMostUsedTag(Pageable pageable);
}
