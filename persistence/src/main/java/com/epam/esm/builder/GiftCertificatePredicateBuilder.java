package com.epam.esm.builder;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.epam.esm.repository.ColumnName.*;

/**
 * The type Gift certificate predicate builder.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public class GiftCertificatePredicateBuilder {
    private final List<Predicate> predicates;
    private final Root<GiftCertificate> root;
    private final CriteriaBuilder builder;

    /**
     * Instantiates a new Gift certificate predicate builder.
     *
     * @param builder the builder
     * @param root    the root
     */
    public GiftCertificatePredicateBuilder(CriteriaBuilder builder, Root<GiftCertificate> root) {
        predicates = new ArrayList<>();
        this.builder = builder;
        this.root = root;
    }

    /**
     * Sets name.
     *
     * @param name the name
     * @return the name
     */
    public GiftCertificatePredicateBuilder setName(String name) {
        if (name != null) {
            predicates.add(builder.like(root.get(NAME), name));
        }
        return this;
    }


    /**
     * Sets description.
     *
     * @param description the description
     * @return the description
     */
    public GiftCertificatePredicateBuilder setDescription(String description) {
        if (description != null) {
            predicates.add(builder.like(root.get(DESCRIPTION), description));
        }
        return this;
    }

    /**
     * Sets price.
     *
     * @param price the price
     * @return the price
     */
    public GiftCertificatePredicateBuilder setPrice(BigDecimal price) {
        if (price != null) {
            predicates.add(builder.equal(root.get(PRICE), price));
        }
        return this;
    }

    /**
     * Sets duration.
     *
     * @param duration the duration
     * @return the duration
     */
    public GiftCertificatePredicateBuilder setDuration(int duration) {
        if (duration != 0) {
            predicates.add(builder.equal(root.get(DURATION), duration));
        }
        return this;
    }

    /**
     * Sets create date.
     *
     * @param createDate the create date
     * @return the create date
     */
    public GiftCertificatePredicateBuilder setCreateDate(LocalDateTime createDate) {
        if (createDate != null) {
            predicates.add(builder.equal(root.get(CREATE_DATE), createDate));
        }
        return this;
    }

    /**
     * Sets last update date.
     *
     * @param lastUpdateDate the last update date
     * @return the last update date
     */
    public GiftCertificatePredicateBuilder setLastUpdateDate(LocalDateTime lastUpdateDate) {
        if (lastUpdateDate != null) {
            predicates.add(builder.equal(root.get(LAST_UPDATE_DATE), lastUpdateDate));
        }
        return this;
    }

    /**
     * Sets tags.
     *
     * @param tags the tags
     * @return the tags
     */
    public GiftCertificatePredicateBuilder setTags(Set<Tag> tags) {
        if (tags != null) {
            for (Tag tag : tags) {
                predicates.add(builder.like(root.join(TAGS).get(NAME), tag.getName()));
            }
        }
        return this;
    }

    /**
     * Build list.
     *
     * @return the list
     */
    public List<Predicate> build() {
        return predicates;
    }
}
