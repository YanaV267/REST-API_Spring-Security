package com.epam.esm.validator.impl;

import com.epam.esm.validator.GiftCertificateValidator;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.epam.esm.util.ParameterName.*;

@Component
public class GiftCertificateValidatorImpl implements GiftCertificateValidator {
    private static final String NAME_REGEX = "[а-я\\p{Lower}\\p{Digit} _]+";
    private static final String DESCRIPTION_REGEX = "[\\p{Graph} ]+";
    private static final String PRICE_REGEX = "(\\d\\d?\\.)?\\d{1,2}";
    private static final String DURATION_REGEX = "\\d+";

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
    public boolean checkCertificate(Map<String, ?> certificateData) {
        return checkName(String.valueOf(certificateData.get(NAME)))
                && checkDescription(String.valueOf(certificateData.get(DESCRIPTION)))
                && checkPrice(String.valueOf(certificateData.get(PRICE)))
                && checkDuration(String.valueOf(certificateData.get(DURATION)));
    }
}
