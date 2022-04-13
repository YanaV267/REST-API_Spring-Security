package com.epam.esm.validator.impl;

import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.esm.util.ParameterName.*;

/**
 * The type Gift certificate validator.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Service
public class GiftCertificateValidatorImpl implements GiftCertificateValidator {
    private static final String ID_REGEX = "\\d+";
    private static final String NAME_REGEX = "[А-Яа-я\\p{Alnum} _]{1,25}";
    private static final String DESCRIPTION_REGEX = "[\\p{Graph} ]{3,200}";
    private static final String PRICE_REGEX = "((\\d{2,4}\\.\\d{1,2})|(\\d{2,4}))";
    private static final String DURATION_REGEX = "\\d+";
    private static final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}";

    private final TagValidator tagValidator;

    /**
     * Instantiates a new Gift certificate validator.
     *
     * @param tagValidator the tag validator
     */
    @Autowired
    public GiftCertificateValidatorImpl(TagValidator tagValidator) {
        this.tagValidator = tagValidator;
    }

    @Override
    public boolean checkId(String id) {
        return id != null && id.matches(ID_REGEX) && Long.parseLong(id) > 0;
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
        return price != null && price.matches(PRICE_REGEX)
                && !(new BigDecimal(price)).equals(BigDecimal.ZERO);
    }

    @Override
    public boolean checkDuration(String duration) {
        return duration != null && duration.matches(DURATION_REGEX)
                && !(new BigDecimal(duration)).equals(BigDecimal.ZERO);
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
    public boolean checkAllCertificateData(Map<String, ?> certificateData) {
        return checkName((String) certificateData.get(NAME))
                && checkDescription((String) certificateData.get(DESCRIPTION))
                && checkPrice((String) certificateData.get(PRICE))
                && checkDuration((String) certificateData.get(DURATION))
                && ((ArrayList<Map<String, String>>) certificateData.get(TAGS)).stream()
                .map(t -> t.get(NAME))
                .map(tagValidator::checkName)
                .noneMatch(r -> r.equals(Boolean.FALSE));
    }

    @Override
    public boolean checkCertificateData(Map<String, ?> certificateData) {
        if (certificateData.containsKey(NAME) && !checkName((String) certificateData.get(NAME))) {
            return false;
        }
        if (certificateData.containsKey(DESCRIPTION)
                && !checkDescription((String) certificateData.get(DESCRIPTION))) {
            return false;
        }
        if (certificateData.containsKey(PRICE) && !checkPrice((String) certificateData.get(PRICE))) {
            return false;
        }
        if (certificateData.containsKey(DURATION)
                && !checkDuration((String) certificateData.get(DURATION))) {
            return false;
        }
        if (certificateData.containsKey(CREATE_DATE)
                && !checkCreateDate((String) certificateData.get(CREATE_DATE))) {
            return false;
        }
        if (certificateData.containsKey(LAST_UPDATE_DATE)
                && !checkLastUpdateDate((String) certificateData.get(LAST_UPDATE_DATE))) {
            return false;
        }
        if (certificateData.containsKey(TAGS)) {
            List<Map<String, String>> tags = (ArrayList<Map<String, String>>) certificateData.get(TAGS);
            return tags.stream()
                    .map(t -> t.get(NAME))
                    .map(tagValidator::checkName)
                    .noneMatch(r -> r.equals(Boolean.FALSE));
        }
        if (certificateData.containsKey(TAG)) {
            return tagValidator.checkName((String) certificateData.get(TAG));
        }
        return true;
    }
}
