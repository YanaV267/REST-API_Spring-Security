package com.epam.esm.builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.epam.esm.repository.ColumnName.*;

/**
 * The type Order query builder.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public class OrderQueryBuilder {
    private static final String WHERE_CLAUSE = " WHERE ";
    private static final String SET_OPERATOR = " SET ";
    private static final String QUOTE = "'";
    private static final String COMMA = ", ";
    private static final String EQUALS = " = ";
    private static final String AND_OPERATOR = " AND ";
    private static final String LIKE_OPERATOR = " LIKE ";
    private static final String LIKE_WILDCARD = "%";
    private final StringBuilder query;

    /**
     * Instantiates a new Order query builder.
     *
     * @param query the query
     */
    public OrderQueryBuilder(String query) {
        this.query = new StringBuilder(query);
    }

    /**
     * Add id parameter order query builder.
     *
     * @param id the id
     * @return the order query builder
     */
    public OrderQueryBuilder addIdParameter(long id) {
        if (id != 0) {
            query.append(ORDER_ID)
                    .append(EQUALS)
                    .append(id);
        }
        return this;
    }

    /**
     * Add user id parameter order query builder.
     *
     * @param userId the user id
     * @return the order query builder
     */
    public OrderQueryBuilder addUserIdParameter(long userId) {
        if (userId != 0) {
            query.append(USER_ID)
                    .append(EQUALS)
                    .append(userId);
        }
        return this;
    }

    /**
     * Add certificate id parameter order query builder.
     *
     * @param certificateId the certificate id
     * @return the order query builder
     */
    public OrderQueryBuilder addCertificateIdParameter(long certificateId) {
        if (certificateId != 0) {
            query.append(GIFT_CERTIFICATE_ID)
                    .append(EQUALS)
                    .append(certificateId);
        }
        return this;
    }

    /**
     * Add certificate name like parameter order query builder.
     *
     * @param certificateName the certificate name
     * @return the order query builder
     */
    public OrderQueryBuilder addCertificateNameLikeParameter(String certificateName) {
        if (certificateName != null) {
            query.append(GIFT_CERTIFICATE_NAME)
                    .append(LIKE_OPERATOR)
                    .append(QUOTE)
                    .append(LIKE_WILDCARD)
                    .append(certificateName)
                    .append(LIKE_WILDCARD)
                    .append(QUOTE);
        }
        return this;
    }

    /**
     * Add user name like parameter order query builder.
     *
     * @param userName the user name
     * @return the order query builder
     */
    public OrderQueryBuilder addUserNameLikeParameter(String userName) {
        if (userName != null) {
            query.append(USER_NAME)
                    .append(LIKE_OPERATOR)
                    .append(QUOTE)
                    .append(LIKE_WILDCARD)
                    .append(userName)
                    .append(LIKE_WILDCARD)
                    .append(QUOTE);
        }
        return this;
    }


    /**
     * Add user surname like parameter order query builder.
     *
     * @param userSurname the user surname
     * @return the order query builder
     */
    public OrderQueryBuilder addUserSurnameLikeParameter(String userSurname) {
        if (userSurname != null) {
            query.append(USER_SURNAME)
                    .append(LIKE_OPERATOR)
                    .append(QUOTE)
                    .append(LIKE_WILDCARD)
                    .append(userSurname)
                    .append(LIKE_WILDCARD)
                    .append(QUOTE);
        }
        return this;
    }

    /**
     * Add cost parameter order query builder.
     *
     * @param cost the cost
     * @return the order query builder
     */
    public OrderQueryBuilder addCostParameter(BigDecimal cost) {
        if (cost != null) {
            checkQueryEnding();
            query.append(ORDER_COST)
                    .append(EQUALS)
                    .append(cost.doubleValue());
        }
        return this;
    }

    /**
     * Add create date parameter order query builder.
     *
     * @param createDate the create date
     * @return the order query builder
     */
    public OrderQueryBuilder addCreateDateParameter(LocalDateTime createDate) {
        if (createDate != null) {
            checkQueryEnding();
            query.append(ORDER_CREATE_DATE)
                    .append(EQUALS)
                    .append(QUOTE)
                    .append(createDate)
                    .append(QUOTE);
        }
        return this;
    }

    /**
     * Add where clause order query builder.
     *
     * @param id the id
     * @return the order query builder
     */
    public OrderQueryBuilder addWhereClause(long id) {
        if (id != 0) {
            query.append(WHERE_CLAUSE);
        }
        return this;
    }

    /**
     * Add where clause order query builder.
     *
     * @return the order query builder
     */
    public OrderQueryBuilder addWhereClause() {
        query.append(WHERE_CLAUSE);
        return this;
    }

    /**
     * Check query ending order query builder.
     *
     * @return the order query builder
     */
    public OrderQueryBuilder checkQueryEnding() {
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
