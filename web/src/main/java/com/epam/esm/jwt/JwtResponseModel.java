package com.epam.esm.jwt;

import java.io.Serializable;

/**
 * @author YanaV
 * @project GiftCertificate
 */
public class JwtResponseModel implements Serializable {
    private final String token;

    public JwtResponseModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
