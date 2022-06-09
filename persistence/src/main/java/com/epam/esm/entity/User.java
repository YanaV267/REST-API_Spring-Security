package com.epam.esm.entity;

import com.epam.esm.entity.converter.UserRoleConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.EntityListeners;
import javax.persistence.Convert;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type User.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Entity
@EntityListeners(AuditListener.class)
@Table(name = "users")
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String login;
    private String password;
    private String surname;
    private String name;
    private BigDecimal balance;
    @Convert(converter = UserRoleConverter.class)
    private UserRole role;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Order> orders;

    /**
     * Instantiates a new User.
     */
    public User() {
        orders = new LinkedHashSet<>();
        role = UserRole.USER;
    }

    /**
     * Instantiates a new User.
     *
     * @param id the id
     */
    public User(long id) {
        this.id = id;
        orders = new LinkedHashSet<>();
        role = UserRole.USER;
    }

    public enum UserRole {
        USER, ADMIN
    }
}
