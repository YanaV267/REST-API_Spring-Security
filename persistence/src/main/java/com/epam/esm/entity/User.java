package com.epam.esm.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
@Table(name = "users")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class User {
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
    private Set<Order> orders;

    /**
     * Instantiates a new User.
     *
     * @param id the id
     */
    public User(long id) {
        this.id = id;
        orders = new LinkedHashSet<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("id=").append(id);
        sb.append(" , login='").append(login);
        sb.append("' , surname='").append(surname);
        sb.append("' , name='").append(name);
        sb.append("' , balance=").append(balance);
        sb.append(" , orders=").append(orders).append("}");
        return sb.toString();
    }
}
