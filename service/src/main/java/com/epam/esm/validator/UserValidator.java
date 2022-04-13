package com.epam.esm.validator;

import java.util.Map;

/**
 * The interface User validator.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface UserValidator {
    /**
     * Check login boolean.
     *
     * @param login the login
     * @return the boolean
     */
    boolean checkLogin(String login);

    /**
     * Check surname boolean.
     *
     * @param surname the surname
     * @return the boolean
     */
    boolean checkSurname(String surname);

    /**
     * Check name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    boolean checkName(String name);

    /**
     * Check user data boolean.
     *
     * @param userData the user data
     * @return the boolean
     */
    boolean checkUserData(Map<String, Object> userData);
}
