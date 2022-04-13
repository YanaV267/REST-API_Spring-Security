package com.epam.esm.validator.impl;

import com.epam.esm.validator.OrderValidator;
import com.epam.esm.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

import static com.epam.esm.util.ParameterName.*;

/**
 * The type Order validator.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Service
public class OrderValidatorImpl implements OrderValidator {
    private static final String ID_REGEX = "\\d+";
    private static final String COST_REGEX = "((\\d{2,4}\\.\\d{1,2})|(\\d{2,4}))";
    private static final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}";

    private final UserValidator userValidator;

    /**
     * Instantiates a new Order validator.
     *
     * @param userValidator the user validator
     */
    @Autowired
    public OrderValidatorImpl(UserValidator userValidator) {
        this.userValidator = userValidator;
    }

    @Override
    public boolean checkId(String id) {
        return id != null && id.matches(ID_REGEX) && Long.parseLong(id) > 0;
    }

    @Override
    public boolean checkCost(String cost) {
        return cost != null && cost.matches(COST_REGEX)
                && !(new BigDecimal(cost)).equals(BigDecimal.ZERO);
    }

    @Override
    public boolean checkCreateDate(String createDate) {
        return createDate != null && createDate.matches(DATE_REGEX);
    }

    @Override
    public boolean checkAllOrderData(Map<String, Object> orderData) {
        return checkId((String) orderData.get(ID_USER))
                && checkId((String) orderData.get(ID_CERTIFICATE))
                && checkCost((String) orderData.get(COST))
                && checkCreateDate((String) orderData.get(CREATE_DATE));
    }

    @Override
    public boolean checkOrderData(Map<String, Object> orderData) {
        if (orderData.containsKey(ID_USER) && !checkId((String) orderData.get(ID_USER))) {
            return false;
        }
        if (orderData.containsKey(ID_CERTIFICATE) && !checkId((String) orderData.get(ID_CERTIFICATE))) {
            return false;
        }
        if (orderData.containsKey(COST) && !checkCost((String) orderData.get(COST))) {
            return false;
        }
        if (orderData.containsKey(CREATE_DATE) && !checkCreateDate((String) orderData.get(CREATE_DATE))) {
            return false;
        }
        return userValidator.checkUserData(orderData);
    }
}
