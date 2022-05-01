package com.epam.esm.builder;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.epam.esm.repository.ColumnName.*;

/**
 * The type Gift certificate update builder.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public class GiftCertificateUpdateBuilder {
    private final Root<GiftCertificate> root;
    private CriteriaUpdate<GiftCertificate> query;

    /**
     * Instantiates a new Gift certificate update builder.
     *
     * @param query the query
     * @param root  the root
     */
    public GiftCertificateUpdateBuilder(CriteriaUpdate<GiftCertificate> query, Root<GiftCertificate> root) {
        this.query = query;
        this.root = root;
    }

    /**
     * Sets name.
     *
     * @param name the name
     * @return the name
     */
    public GiftCertificateUpdateBuilder setName(String name) {
        if (name != null) {
            query = query.set(root.get(NAME), name);
        }
        return this;
    }


    /**
     * Sets description.
     *
     * @param description the description
     * @return the description
     */
    public GiftCertificateUpdateBuilder setDescription(String description) {
        if (description != null) {
            query = query.set(root.get(DESCRIPTION), description);
        }
        return this;
    }

    /**
     * Sets price.
     *
     * @param price the price
     * @return the price
     */
    public GiftCertificateUpdateBuilder setPrice(BigDecimal price) {
        if (price != null) {
            query = query.set(root.get(PRICE), price);
        }
        return this;
    }

    /**
     * Sets duration.
     *
     * @param duration the duration
     * @return the duration
     */
    public GiftCertificateUpdateBuilder setDuration(int duration) {
        if (duration != 0) {
            query = query.set(root.get(DURATION), duration);
        }
        return this;
    }

    /**
     * Sets last update date.
     *
     * @return the last update date
     */
    public GiftCertificateUpdateBuilder setLastUpdateDate() {
        query = query.set(root.get(LAST_UPDATE_DATE), LocalDateTime.now());
        return this;
    }

    /**
     * Build criteria update.
     *
     * @return the criteria update
     */
    public CriteriaUpdate<GiftCertificate> build() {
        return query;
    }
}
