package com.epam.esm.exception;

public class BadRequestException extends RuntimeException {
    private Class<?> resourceClass;

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    public BadRequestException(Class<?> resourceClass) {
        super();
        this.resourceClass = resourceClass;
    }

    public Class<?> getResourceClass() {
        return resourceClass;
    }
}
