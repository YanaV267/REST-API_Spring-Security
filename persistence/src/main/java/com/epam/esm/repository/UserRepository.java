package com.epam.esm.repository;

import com.epam.esm.entity.User;
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
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    /**
     * Update balance.
     *
     * @param balance the new balance
     * @param id      the user id
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
}
