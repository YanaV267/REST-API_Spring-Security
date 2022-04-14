package com.epam.esm.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class GiftCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
    @OneToMany
    @JoinTable(name = "certificate_purchase",
            joinColumns = {@JoinColumn(name = "id_certificate")},
            inverseJoinColumns = {@JoinColumn(name = "id_tag")})
    private Set<Tag> tags;

    /**
     * Instantiates a new Gift certificate.
     *
     * @param id the id
     */
    public GiftCertificate(long id) {
        this.id = id;
        tags = new LinkedHashSet<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("id=").append(id);
        sb.append(" , name='").append(name);
        sb.append("' , description='").append(description);
        sb.append("' , price=").append(price);
        sb.append(" , duration=").append(duration);
        sb.append(" , createDate='").append(createDate);
        sb.append("' , lastUpdateDate='").append(lastUpdateDate).append("'}");
        sb.append("' , tags=").append(tags.toString()).append("}");
        return sb.toString();
    }

    /**
     * The type Gift certificate builder.
     */
    @NoArgsConstructor
    public static class GiftCertificateBuilder {
        private GiftCertificate certificate;

        /**
         * Sets id.
         *
         * @param id the id
         * @return the id
         */
        public GiftCertificateBuilder setId(long id) {
            certificate.id = id;
            return this;
        }

        /**
         * Sets name.
         *
         * @param name the name
         * @return the name
         */
        public GiftCertificateBuilder setName(String name) {
            if (name != null) {
                certificate.name = name;
            }
            return this;
        }

        /**
         * Sets description.
         *
         * @param description the description
         * @return the description
         */
        public GiftCertificateBuilder setDescription(String description) {
            if (description != null) {
                certificate.description = description;
            }
            return this;
        }

        /**
         * Sets price.
         *
         * @param price the price
         * @return the price
         */
        public GiftCertificateBuilder setPrice(BigDecimal price) {
            if (price != null) {
                certificate.price = price;
            }
            return this;
        }

        /**
         * Sets duration.
         *
         * @param duration the duration
         * @return the duration
         */
        public GiftCertificateBuilder setDuration(int duration) {
            if (duration != 0) {
                certificate.duration = duration;
            }
            return this;
        }

        /**
         * Sets create date.
         *
         * @param createDate the create date
         * @return the create date
         */
        public GiftCertificateBuilder setCreateDate(LocalDateTime createDate) {
            if (createDate != null) {
                certificate.createDate = createDate;
            }
            return this;
        }

        /**
         * Sets last update date.
         *
         * @param lastUpdateDate the last update date
         * @return the last update date
         */
        public GiftCertificateBuilder setLastUpdateDate(LocalDateTime lastUpdateDate) {
            if (lastUpdateDate != null) {
                certificate.lastUpdateDate = lastUpdateDate;
            }
            return this;
        }

        /**
         * Sets tags.
         *
         * @param tags the tags
         * @return the tags
         */
        public GiftCertificateBuilder setTags(Set<Tag> tags) {
            if (tags != null && !tags.isEmpty()) {
                certificate.tags = tags;
            }
            return this;
        }

        /**
         * Build gift certificate.
         *
         * @return the gift certificate
         */
        public GiftCertificate build() {
            return certificate;
        }
    }
}
