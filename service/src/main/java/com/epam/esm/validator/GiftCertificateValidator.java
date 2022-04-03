package com.epam.esm.validator;

import java.util.Map;

public interface GiftCertificateValidator {
    boolean checkName(String name);

    boolean checkDescription(String description);

    boolean checkPrice(String price);

    boolean checkDuration(String duration);

    boolean checkCertificate(Map<String, ?> certificateData);
}
