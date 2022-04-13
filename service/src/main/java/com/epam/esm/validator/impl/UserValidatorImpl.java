package com.epam.esm.validator.impl;

import com.epam.esm.validator.UserValidator;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.epam.esm.util.ParameterName.*;

/**
 * The type User validator.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Service
public class UserValidatorImpl implements UserValidator {
    private static final String LOGIN_REGEX = "[\\p{Alnum}_]{1,30}";
    private static final String SURNAME_REGEX = "[А-Яа-я\\p{Alpha}]{1,30}";
    private static final String NAME_REGEX = "[А-Яа-я\\p{Alpha}]{1,25}";

    @Override
    public boolean checkLogin(String login) {
        return login != null && login.matches(LOGIN_REGEX);
    }

    @Override
    public boolean checkSurname(String surname) {
        return surname != null && surname.matches(SURNAME_REGEX);
    }

    @Override
    public boolean checkName(String name) {
        return name != null && name.matches(NAME_REGEX);
    }

    @Override
    public boolean checkUserData(Map<String, Object> userData) {
        if (userData.containsKey(LOGIN) && !checkLogin((String) userData.get(LOGIN))) {
            return false;
        }
        if (userData.containsKey(SURNAME) && !checkSurname((String) userData.get(SURNAME))) {
            return false;
        }
        return !userData.containsKey(NAME) || checkName((String) userData.get(NAME));
    }
}
