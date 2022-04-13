package com.epam.esm.validator.impl;

import com.epam.esm.validator.TagValidator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Tag validator.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Service
public class TagValidatorImpl implements TagValidator {
    private static final String NAME_REGEX = "[а-я\\p{Lower} _]{2,50}";

    @Override
    public boolean checkName(String name) {
        return name != null && name.matches(NAME_REGEX);
    }

    @Override
    public boolean checkNames(List<String> names) {
        return names.stream()
                .map(this::checkName)
                .noneMatch(r -> r.equals(Boolean.FALSE));
    }
}
