package com.epam.esm.exception;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;

import java.util.Arrays;

/**
 * The enum Resource code.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public enum ResourceCode {
    /**
     * Default resource code.
     */
    DEFAULT(Object.class, "00"),
    /**
     * Certificate resource code.
     */
    CERTIFICATE(GiftCertificateDto.class, "01"),
    /**
     * Tag resource code.
     */
    TAG(TagDto.class, "02");

    private final Class<?> resourceClass;
    private final String resourceCode;

    ResourceCode(Class<?> resourceClass, String resourceCode) {
        this.resourceClass = resourceClass;
        this.resourceCode = resourceCode;
    }

    /**
     * Find resource code string.
     *
     * @param currentClass the current class
     * @return the string
     */
    public static String findResourceCode(Class<?> currentClass) {
        return Arrays.stream(values())
                .filter(r -> r.resourceClass.equals(currentClass))
                .map(ResourceCode::getResourceCode)
                .findFirst()
                .orElseGet(DEFAULT::getResourceCode);
    }

    /**
     * Gets resource code.
     *
     * @return the resource code
     */
    public String getResourceCode() {
        return resourceCode;
    }
}
