package com.epam.esm.exception;

import static com.epam.esm.util.ParameterName.CERTIFICATES;
import static com.epam.esm.util.ParameterName.TAGS;

/**
 * The type No data found exception.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public class NoDataFoundException extends RuntimeException {
    private static final String DELIMITER = " = ";
    private static final String ALL_DATA_VALUE = "all";
    private Class<?> resourceClass;
    private String parameters;

    /**
     * Instantiates a new No data found exception.
     */
    public NoDataFoundException() {
        super();
    }

    /**
     * Instantiates a new No data found exception.
     *
     * @param message the message
     */
    public NoDataFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new No data found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public NoDataFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new No data found exception.
     *
     * @param cause the cause
     */
    public NoDataFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new No data found exception.
     *
     * @param parameters    the parameters
     * @param resourceClass the resource class
     */
    public NoDataFoundException(String parameters, Class<?> resourceClass) {
        super();
        if (parameters.equals(CERTIFICATES) || parameters.equals(TAGS)) {
            this.parameters = parameters + DELIMITER + ALL_DATA_VALUE;
        } else {
            this.parameters = parameters.replaceAll(DELIMITER.trim(), DELIMITER);
        }
        this.resourceClass = resourceClass;
    }

    /**
     * Instantiates a new No data found exception.
     *
     * @param parameterType the parameter type
     * @param parameter     the parameter
     * @param resourceClass the resource class
     */
    public NoDataFoundException(String parameterType, Object parameter, Class<?> resourceClass) {
        super();
        this.parameters = parameterType + DELIMITER + parameter;
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

    /**
     * Gets parameters.
     *
     * @return the parameters
     */
    public String getParameters() {
        return parameters;
    }
}
