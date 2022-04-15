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

/**
 * The type Gift certificate.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Entity
@Table(name = "gift_certificates")
@Data
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
    @Column(name = "last_update_date", insertable = false, updatable = false)
    private LocalDateTime lastUpdateDate;
    @OneToMany(cascade = CascadeType.ALL)
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
}
