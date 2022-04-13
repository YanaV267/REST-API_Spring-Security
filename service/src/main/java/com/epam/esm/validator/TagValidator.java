package com.epam.esm.validator;

import java.util.List;

/**
 * The interface Tag validator.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface TagValidator {
    /**
     * Check name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    boolean checkName(String name);

    /**
     * Check names boolean.
     *
     * @param names the names
     * @return the boolean
     */
    boolean checkNames(List<String> names);
}
