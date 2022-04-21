package com.epam.esm.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
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
@Getter
@EqualsAndHashCode
@ToString
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String login;
    @Column
    private String surname;
    @Column
    private String name;
    @Column
    private BigDecimal balance;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Order> orders;

    /**
     * Instantiates a new User.
     */
    public User() {
        orders = new LinkedHashSet<>();
    }

    /**
     * Instantiates a new User.
     *
     * @param id the id
     */
    public User(long id) {
        this.id = id;
        orders = new LinkedHashSet<>();
    }
}
