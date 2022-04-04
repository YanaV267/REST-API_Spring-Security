package com.epam.esm.exception;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;

import java.util.Arrays;

public enum ResourceCode {
    DEFAULT(Object.class, "00"),
    CERTIFICATE(GiftCertificateDto.class, "01"),
    TAG(TagDto.class, "02");

    private final Class<?> resourceClass;
    private final String resourceCode;

    ResourceCode(Class<?> resourceClass, String resourceCode) {
        this.resourceClass = resourceClass;
        this.resourceCode = resourceCode;
    }

    public static String findResourceCode(Class<?> currentClass) {
        return Arrays.stream(values())
                .filter(r -> r.resourceClass.equals(currentClass))
                .map(ResourceCode::getResourceCode)
                .findFirst()
                .orElseGet(DEFAULT::getResourceCode);
    }

    public String getResourceCode() {
        return resourceCode;
    }
}
