package com.epam.esm.exception;

public class ResponseErrorEntity {
    private final String errorCode;
    private final String errorMessage;

    public ResponseErrorEntity(long errorCode, Class<?> resourceClass, String errorMessage) {
        this.errorCode = errorCode + ResourceCode.findResourceCode(resourceClass);
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
