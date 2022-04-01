package com.epam.esm.validator;

import com.epam.esm.util.ParameterName;

import java.util.Map;

public interface GiftCertificateValidator {
    boolean checkName(String name);

    boolean checkDescription(String description);

    boolean checkPrice(String price);

    boolean checkDuration(String duration);

    boolean checkCreateDate(String createDate);

    boolean checkLastUpdateDate(String lastUpdateDate);

    boolean checkCertificate(Map<String, String> certificateData);
}
