package com.epam.esm.builder;

import com.epam.esm.repository.ColumnName;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.epam.esm.repository.ColumnName.*;

/**
 * The type Gift certificate query builder.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public class GiftCertificateQueryBuilder {
    private static final String WHERE_CLAUSE = " WHERE ";
    private static final String SET_OPERATOR = " SET ";
    private static final String SPACE = " ";
    private static final String QUOTE = "'";
    private static final String COMMA = ", ";
    private static final String UNDERSCORE = "_";
    private static final String EQUALS = " = ";
    private static final String AND_OPERATOR = " AND ";
    private static final String LIKE_OPERATOR = " LIKE ";
    private static final String LIKE_WILDCARD = "%";
    private static final String ORDER_BY_OPERATOR = " ORDER BY ";
    private static final String DESC_OPERATOR = "DESC";
    private static final String NOW_TIME = "now(3)";
    private final StringBuilder query;

    /**
     * Instantiates a new Gift certificate query builder.
     *
     * @param query the query
     */
    public GiftCertificateQueryBuilder(String query) {
        this.query = new StringBuilder(query);
    }

    /**
     * Add id parameter gift certificate query builder.
     *
     * @param id the id
     * @return the gift certificate query builder
     */
    public GiftCertificateQueryBuilder addIdParameter(long id) {
        if (id != 0) {
            query.append(GIFT_CERTIFICATE_ID)
                    .append(EQUALS)
                    .append(id);
        }
        return this;
    }

    /**
     * Add name parameter gift certificate query builder.
     *
     * @param name the name
     * @return the gift certificate query builder
     */
    public GiftCertificateQueryBuilder addNameParameter(String name) {
        if (name != null) {
            query.append(GIFT_CERTIFICATE_NAME)
                    .append(EQUALS)
                    .append(QUOTE)
                    .append(name)
                    .append(QUOTE);
        }
        return this;
    }

    /**
     * Add name like parameter gift certificate query builder.
     *
     * @param name the name
     * @return the gift certificate query builder
     */
    public GiftCertificateQueryBuilder addNameLikeParameter(String name) {
        if (name != null) {
            query.append(GIFT_CERTIFICATE_NAME)
                    .append(LIKE_OPERATOR)
                    .append(QUOTE)
                    .append(LIKE_WILDCARD)
                    .append(name)
                    .append(LIKE_WILDCARD)
                    .append(QUOTE);
        }
        return this;
    }

    /**
     * Add description parameter gift certificate query builder.
     *
     * @param description the description
     * @return the gift certificate query builder
     */
    public GiftCertificateQueryBuilder addDescriptionParameter(String description) {
        if (description != null) {
            checkQueryEnding();
            query.append(DESCRIPTION)
                    .append(EQUALS)
                    .append(QUOTE)
                    .append(description)
                    .append(QUOTE);
        }
        return this;
    }


    /**
     * Add description like parameter gift certificate query builder.
     *
     * @param description the description
     * @return the gift certificate query builder
     */
    public GiftCertificateQueryBuilder addDescriptionLikeParameter(String description) {
        if (description != null) {
            if (!query.toString().endsWith(WHERE_CLAUSE)) {
                query.append(AND_OPERATOR);
            }
            query.append(DESCRIPTION)
                    .append(LIKE_OPERATOR)
                    .append(QUOTE)
                    .append(LIKE_WILDCARD)
                    .append(description)
                    .append(LIKE_WILDCARD)
                    .append(QUOTE);
        }
        return this;
    }

    /**
     * Add price parameter gift certificate query builder.
     *
     * @param price the price
     * @return the gift certificate query builder
     */
    public GiftCertificateQueryBuilder addPriceParameter(BigDecimal price) {
        if (price != null) {
            checkQueryEnding();
            query.append(PRICE)
                    .append(EQUALS)
                    .append(price.doubleValue());
        }
        return this;
    }

    /**
     * Add duration parameter gift certificate query builder.
     *
     * @param duration the duration
     * @return the gift certificate query builder
     */
    public GiftCertificateQueryBuilder addDurationParameter(int duration) {
        if (duration != 0) {
            checkQueryEnding();
            query.append(DURATION)
                    .append(EQUALS)
                    .append(duration);
        }
        return this;
    }

    /**
     * Add create date parameter gift certificate query builder.
     *
     * @param createDate the create date
     * @return the gift certificate query builder
     */
    public GiftCertificateQueryBuilder addCreateDateParameter(LocalDateTime createDate) {
        if (createDate != null) {
            checkQueryEnding();
            query.append(CREATE_DATE)
                    .append(EQUALS)
                    .append(QUOTE)
                    .append(createDate)
                    .append(QUOTE);
        }
        return this;
    }

    /**
     * Add last update date parameter gift certificate query builder.
     *
     * @param lastUpdateDate the last update date
     * @return the gift certificate query builder
     */
    public GiftCertificateQueryBuilder addLastUpdateDateParameter(LocalDateTime lastUpdateDate) {
        if (lastUpdateDate != null) {
            checkQueryEnding();
            query.append(LAST_UPDATE_DATE)
                    .append(EQUALS)
                    .append(QUOTE)
                    .append(lastUpdateDate)
                    .append(QUOTE);
        }
        return this;
    }

    /**
     * Add last update date now parameter gift certificate query builder.
     *
     * @return the gift certificate query builder
     */
    public GiftCertificateQueryBuilder addLastUpdateDateNowParameter() {
        query.append(LAST_UPDATE_DATE)
                .append(EQUALS)
                .append(NOW_TIME);
        return this;
    }

    /**
     * Add tag name gift certificate query builder.
     *
     * @param tagName the tag name
     * @return the gift certificate query builder
     */
    public GiftCertificateQueryBuilder addTagName(String tagName) {
        if (tagName != null) {
            if (!query.toString().endsWith(WHERE_CLAUSE)) {
                query.append(AND_OPERATOR);
            }
            query.append(TAG_NAME)
                    .append(EQUALS)
                    .append(QUOTE)
                    .append(tagName)
                    .append(QUOTE);
        }
        return this;
    }

    /**
     * Add sorting gift certificate query builder.
     *
     * @param sortTypes the sort types
     * @return the gift certificate query builder
     */
    public GiftCertificateQueryBuilder addSorting(List<String> sortTypes) {
        if (sortTypes != null) {
            query.append(ORDER_BY_OPERATOR);
            for (String sortType : sortTypes) {
                if (!query.toString().endsWith(ORDER_BY_OPERATOR)) {
                    query.append(COMMA);
                }
                if (!sortType.substring(0, sortType.indexOf(UNDERSCORE)).equals(ColumnName.NAME)) {
                    query.append(sortType, 0, sortType.indexOf(UNDERSCORE));
                } else {
                    query.append(ColumnName.GIFT_CERTIFICATE_NAME);
                }
                if (sortType.endsWith(DESC_OPERATOR.toLowerCase())) {
                    query.append(SPACE).append(DESC_OPERATOR);
                }
            }
        }
        return this;
    }

    /**
     * Add where clause gift certificate query builder.
     *
     * @param id the id
     * @return the gift certificate query builder
     */
    public GiftCertificateQueryBuilder addWhereClause(long id) {
        if (id != 0) {
            query.append(WHERE_CLAUSE);
        }
        return this;
    }

    /**
     * Add where clause gift certificate query builder.
     *
     * @return the gift certificate query builder
     */
    public GiftCertificateQueryBuilder addWhereClause() {
        query.append(WHERE_CLAUSE);
        return this;
    }

    /**
     * Check query ending.
     *
     * @return the gift certificate query builder
     */
    public GiftCertificateQueryBuilder checkQueryEnding() {
        if (query.toString().contains(WHERE_CLAUSE) && !query.toString().endsWith(WHERE_CLAUSE)) {
            query.append(AND_OPERATOR);
        }
        if (query.toString().contains(SET_OPERATOR) && !query.toString().endsWith(SET_OPERATOR)) {
            query.append(COMMA);
        }
        return this;
    }

    /**
     * Build string.
     *
     * @return the string
     */
    public String build() {
        return query.toString();
    }
}
