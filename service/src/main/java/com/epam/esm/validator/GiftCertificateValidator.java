package com.epam.esm.validator;

import java.util.Map;

/**
 * The interface Gift certificate validator.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface GiftCertificateValidator {
    /**
     * Check name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    boolean checkName(String name);

    /**
     * Check description boolean.
     *
     * @param description the description
     * @return the boolean
     */
    boolean checkDescription(String description);

    /**
     * Check price boolean.
     *
     * @param price the price
     * @return the boolean
     */
    boolean checkPrice(String price);

    /**
     * Check duration boolean.
     *
     * @param duration the duration
     * @return the boolean
     */
    boolean checkDuration(String duration);

    /**
     * Check start date boolean.
     *
     * @param createDate the create date
     * @return the boolean
     */
    boolean checkCreateDate(String createDate);

    /**
     * Check last update date boolean.
     *
     * @param lastUpdateDate the last update date
     * @return the boolean
     */
    boolean checkLastUpdateDate(String lastUpdateDate);

    /**
     * Check certificate boolean.
     *
     * @param certificateData the certificate data
     * @return the boolean
     */
    boolean checkCertificate(Map<String, ?> certificateData);
}
