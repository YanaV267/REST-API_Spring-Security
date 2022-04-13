package com.epam.esm.entity;

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
public class GiftCertificate {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private Set<Tag> tags;

    /**
     * Instantiates a new Gift certificate.
     */
    public GiftCertificate() {
        tags = new LinkedHashSet<>();
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets duration.
     *
     * @param duration the duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Gets create date.
     *
     * @return the create date
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets create date.
     *
     * @param createDate the create date
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets last update date.
     *
     * @return the last update date
     */
    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * Sets last update date.
     *
     * @param lastUpdateDate the last update date
     */
    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * Gets tags.
     *
     * @return the tags
     */
    public Set<Tag> getTags() {
        return new LinkedHashSet<>(tags);
    }

    /**
     * Sets tags.
     *
     * @param tags the tags
     */
    public void setTags(Set<Tag> tags) {
        this.tags = new LinkedHashSet<>(tags);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GiftCertificate certificate = (GiftCertificate) o;
        if (name != null ? name.equals(certificate.name) : certificate.name == null) {
            return false;
        }
        if (price != null ? price.equals(certificate.price) : certificate.price == null) {
            return false;
        }
        if (description != null ? description.equals(certificate.description) : certificate.description == null) {
            return false;
        }
        if (createDate != null ? createDate.equals(certificate.createDate) : certificate.createDate == null) {
            return false;
        }
        if (lastUpdateDate != null ? lastUpdateDate.equals(certificate.lastUpdateDate) : certificate.lastUpdateDate == null) {
            return false;
        }
        if (tags != null ? tags.equals(certificate.tags) : certificate.tags == null) {
            return false;
        }
        return id == certificate.id && duration == certificate.duration;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 31 + Long.hashCode(id);
        result = result * 31 + (name != null ? name.hashCode() : 0);
        result = result * 31 + (description != null ? description.hashCode() : 0);
        result = result * 31 + (price != null ? price.hashCode() : 0);
        result = result * 31 + Integer.hashCode(duration);
        result = result * 31 + (createDate != null ? createDate.hashCode() : 0);
        result = result * 31 + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = result * 31 + tags.hashCode();
        return result;
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
    public static class GiftCertificateBuilder {
        private final GiftCertificate certificate;

        /**
         * Instantiates a new Gift certificate builder.
         */
        public GiftCertificateBuilder() {
            certificate = new GiftCertificate();
        }

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
