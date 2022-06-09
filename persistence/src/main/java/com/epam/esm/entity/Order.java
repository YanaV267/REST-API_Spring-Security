package com.epam.esm.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.EntityListeners;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.epam.esm.entity.AuditListener.audit;
import static com.epam.esm.entity.AuditListener.auditDateTime;
import static com.epam.esm.entity.AuditListener.INSERT_OPERATION;

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
    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<GiftCertificatePurchase> certificates;

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