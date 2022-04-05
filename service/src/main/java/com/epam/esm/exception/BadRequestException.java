package com.epam.esm.exception;

/**
 * The type Bad request exception.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public class BadRequestException extends RuntimeException {
    private Class<?> resourceClass;

    /**
     * Instantiates a new Bad request exception.
     */
    public BadRequestException() {
        super();
    }

    /**
     * Instantiates a new Bad request exception.
     *
     * @param message the message
     */
    public BadRequestException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Bad request exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Bad request exception.
     *
     * @param cause the cause
     */
    public BadRequestException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Bad request exception.
     *
     * @param resourceClass the resource class
     */
    public BadRequestException(Class<?> resourceClass) {
        super();
        this.resourceClass = resourceClass;
    }

    /**
     * Gets resource class.
     *
     * @return the resource class
     */
    public Class<?> getResourceClass() {
        return resourceClass;
    }
}
