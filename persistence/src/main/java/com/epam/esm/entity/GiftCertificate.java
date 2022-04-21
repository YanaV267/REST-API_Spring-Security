package com.epam.esm.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.epam.esm.entity.AuditListener.*;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

/**
 * The type Gift certificate.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Entity
@Table(name = "gift_certificates")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class GiftCertificate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;

    @Column(name = "create_date", insertable = false)
    private LocalDateTime createDate;

    @Column(name = "last_update_date", insertable = false)
    private LocalDateTime lastUpdateDate;

    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(name = "certificate_purchase",
            joinColumns = {@JoinColumn(name = "id_certificate")},
            inverseJoinColumns = {@JoinColumn(name = "id_tag")})
    private Set<Tag> tags;

    /**
     * Instantiates a new Gift certificate.
     */
    @Tolerate
    public GiftCertificate() {
        tags = new LinkedHashSet<>();
    }

    /**
     * Instantiates a new Gift certificate.
     *
     * @param id the id
     */
    @Tolerate
    public GiftCertificate(long id) {
        this.id = id;
        tags = new LinkedHashSet<>();
    }

    /**
     * On pre persist.
     */
    @PrePersist
    public void onPrePersist() {
        createDate = auditDateTime;
        audit(this, INSERT_OPERATION);
    }

    /**
     * On pre update.
     */
    @PreUpdate
    public void onPreUpdate() {
        lastUpdateDate = auditDateTime;
        audit(this, UPDATE_OPERATION);
    }

    /**
     * On pre remove.
     */
    @PreRemove
    public void onPreRemove() {
        lastUpdateDate = auditDateTime;
        audit(this, DELETE_OPERATION);
    }
}
