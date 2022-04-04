package com.epam.esm.exception;

import static com.epam.esm.util.ParameterName.CERTIFICATES;
import static com.epam.esm.util.ParameterName.TAGS;

public class NoDataFoundException extends RuntimeException {
    private static final String DELIMITER = " = ";
    private static final String ALL_DATA_VALUE = "all";
    private Class<?> resourceClass;
    private String parameters;

    public NoDataFoundException() {
        super();
    }

    public NoDataFoundException(String message) {
        super(message);
    }

    public NoDataFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoDataFoundException(Throwable cause) {
        super(cause);
    }

    public NoDataFoundException(String parameters, Class<?> resourceClass) {
        super();
        if (parameters.equals(CERTIFICATES) || parameters.equals(TAGS)) {
            this.parameters = parameters + DELIMITER + ALL_DATA_VALUE;
        } else {
            this.parameters = parameters.replaceAll(DELIMITER.trim(), DELIMITER);
        }
        this.resourceClass = resourceClass;
    }

    public NoDataFoundException(String parameterType, Object parameter, Class<?> resourceClass) {
        super();
        this.parameters = parameterType + DELIMITER + parameter;
        this.resourceClass = resourceClass;
    }

    public Class<?> getResourceClass() {
        return resourceClass;
    }

    public String getParameters() {
        return parameters;
    }
}
