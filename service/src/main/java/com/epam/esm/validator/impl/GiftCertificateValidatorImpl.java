package com.epam.esm.validator.impl;

import com.epam.esm.validator.GiftCertificateValidator;

import java.util.Map;

import static com.epam.esm.util.ParameterName.*;

public class GiftCertificateValidatorImpl implements GiftCertificateValidator {
    private static final String NAME_REGEX = "[а-я\\p{Lower} _]+";
    private static final String DESCRIPTION_REGEX = "[\\p{Graph} ]+";
    private static final String PRICE_REGEX = "(\\d\\d?\\.)?\\d{1,2}";
    private static final String DURATION_REGEX = "\\d+";
    private static final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}";

    @Override
    public boolean checkName(String name) {
        return name != null && name.matches(NAME_REGEX);
    }

    @Override
    public boolean checkDescription(String description) {
        return description != null && description.matches(DESCRIPTION_REGEX);
    }

    @Override
    public boolean checkPrice(String price) {
        return price != null && price.matches(PRICE_REGEX);
    }

    @Override
    public boolean checkDuration(String duration) {
        return duration != null && duration.matches(DURATION_REGEX);
    }

    @Override
    public boolean checkCreateDate(String createDate) {
        return createDate != null && createDate.matches(DATE_REGEX);
    }

    @Override
    public boolean checkLastUpdateDate(String lastUpdateDate) {
        return lastUpdateDate != null && lastUpdateDate.matches(DATE_REGEX);
    }

    @Override
    public boolean checkCertificate(Map<String, String> certificateData) {
        return checkName(certificateData.get(NAME)) && checkDescription(certificateData.get(DESCRIPTION))
                && checkPrice(certificateData.get(PRICE)) && checkDuration(certificateData.get(DURATION))
                && checkCreateDate(certificateData.get(CREATE_DATE))
                && checkLastUpdateDate(certificateData.get(LAST_UPDATE_DATE));
    }
}
