package com.epam.esm.builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class GiftCertificateQueryBuilder {
    private static final String WHERE_CLAUSE = " WHERE ";
    private static final String SET_OPERATOR = " SET ";
    private static final String COMMA = ", ";
    private static final String AND_OPERATOR = " AND ";
    private static final String ORDER_BY_OPERATOR = " ORDER BY ";
    private final StringBuilder query;

    public GiftCertificateQueryBuilder(String query) {
        this.query = new StringBuilder(query);
    }

    public GiftCertificateQueryBuilder addIdParameter(long id) {
        if (id != 0) {
            query.append("gift_certificate.id = ").append(id);
        }
        return this;
    }

    public GiftCertificateQueryBuilder addNameParameter(String name) {
        if (name != null) {
            query.append("gift_certificate.name = '").append(name).append("'");
        }
        return this;
    }

    public GiftCertificateQueryBuilder addNameLikeParameter(String name) {
        if (name != null) {
            query.append("gift_certificate.name LIKE '%").append(name).append("%'");
        }
        return this;
    }

    public GiftCertificateQueryBuilder addDescriptionParameter(String description) {
        if (description != null) {
            checkQueryEnding();
            query.append("description = '").append(description).append("'");
        }
        return this;
    }


    public GiftCertificateQueryBuilder addDescriptionLikeParameter(String description) {
        if (description != null) {
            if (!query.toString().endsWith(WHERE_CLAUSE)) {
                query.append(AND_OPERATOR);
            }
            query.append("description LIKE '%").append(description).append("%'");
        }
        return this;
    }

    public GiftCertificateQueryBuilder addPriceParameter(BigDecimal price) {
        if (price != null) {
            checkQueryEnding();
            query.append("price = ").append(price.doubleValue());
        }
        return this;
    }

    public GiftCertificateQueryBuilder addDurationParameter(int duration) {
        if (duration != 0) {
            checkQueryEnding();
            query.append("duration = ").append(duration);
        }
        return this;
    }

    public GiftCertificateQueryBuilder addCreateDateParameter(LocalDateTime createDate) {
        if (createDate != null) {
            checkQueryEnding();
            query.append("create_date = '").append(createDate).append("'");
        }
        return this;
    }

    public GiftCertificateQueryBuilder addLastUpdateDateParameter(LocalDateTime lastUpdateDate) {
        if (lastUpdateDate != null) {
            checkQueryEnding();
            query.append("last_update_date = '").append(lastUpdateDate).append("'");
        }
        return this;
    }

    public GiftCertificateQueryBuilder addLastUpdateDateNowParameter() {
        query.append("last_update_date = now(3)");
        return this;
    }

    public GiftCertificateQueryBuilder addTagName(String tagName) {
        if (tagName != null) {
            if (!query.toString().endsWith(WHERE_CLAUSE)) {
                query.append(AND_OPERATOR);
            }
            query.append("tag.name = '").append(tagName).append("'");
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
                query.append(sortType, 0, sortType.indexOf("_"));
                if (sortType.endsWith("desc")) {
                    query.append(" DESC");
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
