package com.epam.esm.jwt;

import java.io.Serializable;

/**
 * The type Jwt response model.
 */
public class JwtResponseModel implements Serializable {
    private final String token;

    /**
     * Instantiates a new Jwt response model.
     *
     * @param token the token
     */
    public JwtResponseModel(String token) {
        this.token = token;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }
}
