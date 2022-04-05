package com.epam.esm.exception;

/**
 * The type Response error entity.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public class ResponseErrorEntity {
    private final String errorCode;
    private final String errorMessage;

    /**
     * Instantiates a new Response error entity.
     *
     * @param errorCode     the error code
     * @param resourceClass the resource class
     * @param errorMessage  the error message
     */
    public ResponseErrorEntity(long errorCode, Class<?> resourceClass, String errorMessage) {
        this.errorCode = errorCode + ResourceCode.findResourceCode(resourceClass);
        this.errorMessage = errorMessage;
    }

    /**
     * Gets error code.
     *
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Gets error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
