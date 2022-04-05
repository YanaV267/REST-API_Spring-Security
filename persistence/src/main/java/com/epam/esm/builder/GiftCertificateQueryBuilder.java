package com.epam.esm.builder;

import com.epam.esm.repository.ColumnName;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.epam.esm.repository.ColumnName.*;

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

    public GiftCertificateQueryBuilder(String query) {
        this.query = new StringBuilder(query);
    }

    public GiftCertificateQueryBuilder addIdParameter(long id) {
        if (id != 0) {
            query.append(GIFT_CERTIFICATE_ID)
                    .append(EQUALS)
                    .append(id);
        }
        return this;
    }

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

    public GiftCertificateQueryBuilder addPriceParameter(BigDecimal price) {
        if (price != null) {
            checkQueryEnding();
            query.append(PRICE)
                    .append(EQUALS)
                    .append(price.doubleValue());
        }
        return this;
    }

    public GiftCertificateQueryBuilder addDurationParameter(int duration) {
        if (duration != 0) {
            checkQueryEnding();
            query.append(DURATION)
                    .append(EQUALS)
                    .append(duration);
        }
        return this;
    }

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

    public GiftCertificateQueryBuilder addLastUpdateDateNowParameter() {
        query.append(LAST_UPDATE_DATE)
                .append(EQUALS)
                .append(NOW_TIME);
        return this;
    }

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

    public GiftCertificateQueryBuilder addWhereClause() {
        query.append(WHERE_CLAUSE);
        return this;
    }

    public void checkQueryEnding() {
        if (!query.toString().endsWith(WHERE_CLAUSE)) {
            query.append(AND_OPERATOR);
        }
        if (!query.toString().endsWith(SET_OPERATOR)) {
            query.append(COMMA);
        }
    }

    public String build() {
        return query.toString();
    }
}
