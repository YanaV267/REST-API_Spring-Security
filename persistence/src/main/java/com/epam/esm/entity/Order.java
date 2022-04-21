package com.epam.esm.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.epam.esm.entity.AuditListener.*;

/**
 * The type Order.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Entity
@EntityListeners(AuditListener.class)
@Table(name = "orders")
@Data
@Builder
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;
    private BigDecimal cost;
    @Column(name = "create_date", insertable = false)
    private LocalDateTime createDate;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "order_purchase",
            joinColumns = {@JoinColumn(name = "id_order")},
            inverseJoinColumns = {@JoinColumn(name = "id_certificate")})
    private Set<GiftCertificate> certificates;

    /**
     * Instantiates a new Order.
     */
    @Tolerate
    public Order() {
        user = new User();
        certificates = new LinkedHashSet<>();
    }

    /**
     * On pre persist.
     */
    @PrePersist()
    public void onPrePersist() {
        createDate = auditDateTime;
        audit(this, INSERT_OPERATION);
    }
}