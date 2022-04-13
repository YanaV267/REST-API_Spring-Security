package com.epam.esm.validator;

import java.util.Map;

/**
 * The interface Order validator.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public interface OrderValidator {
    /**
     * Check id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean checkId(String id);

    /**
     * Check cost boolean.
     *
     * @param cost the cost
     * @return the boolean
     */
    boolean checkCost(String cost);

    /**
     * Check create date boolean.
     *
     * @param createDate the create date
     * @return the boolean
     */
    boolean checkCreateDate(String createDate);

    /**
     * Check all order data boolean.
     *
     * @param orderData the order data
     * @return the boolean
     */
    boolean checkAllOrderData(Map<String, Object> orderData);

    /**
     * Check order data boolean.
     *
     * @param orderData the order data
     * @return the boolean
     */
    boolean checkOrderData(Map<String, Object> orderData);

}
