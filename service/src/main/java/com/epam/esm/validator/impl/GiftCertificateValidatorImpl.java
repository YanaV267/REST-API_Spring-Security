package com.epam.esm.validator.impl;

import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.esm.util.ParameterName.*;

@Component
public class GiftCertificateValidatorImpl implements GiftCertificateValidator {
    private static final String NAME_REGEX = "[а-я\\p{Alnum} _]+";
    private static final String DESCRIPTION_REGEX = "[\\p{Graph} ]+";
    private static final String PRICE_REGEX = "((\\d{2,4}\\.\\d{1,2})|(\\d{2,4}))";
    private static final String DURATION_REGEX = "\\d+";

    private final TagValidator tagValidator;

    @Autowired
    public GiftCertificateValidatorImpl(TagValidator tagValidator) {
        this.tagValidator = tagValidator;
    }

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
        if (certificateData.containsKey(NAME) && !checkName(String.valueOf(certificateData.get(NAME)))) {
            return false;
        }
        if (certificateData.containsKey(DESCRIPTION) && !checkDescription(String.valueOf(certificateData.get(DESCRIPTION)))) {
            return false;
        }
        if (certificateData.containsKey(PRICE) && !checkPrice(String.valueOf(certificateData.get(PRICE)))) {
            return false;
        }
        if (certificateData.containsKey(DURATION) && !checkDuration(String.valueOf(certificateData.get(DURATION)))) {
            return false;
        }
        if (certificateData.containsKey(TAGS)) {
            List<Map<String, String>> tags = (ArrayList<Map<String, String>>) certificateData.get(TAGS);
            return tags.stream()
                    .map(t -> t.get(NAME))
                    .map(tagValidator::checkName)
                    .noneMatch(r -> r.equals(Boolean.FALSE));
        }
        return true;
    }
}
