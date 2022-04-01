package com.epam.esm.validator.impl;

import com.epam.esm.validator.TagValidator;

public class TagValidatorImpl implements TagValidator {
    private static final String NAME_REGEX = "[а-я\\p{Lower} _]+";

    @Override
    public boolean checkName(String name) {
        return name != null && name.matches(NAME_REGEX);
    }
}
