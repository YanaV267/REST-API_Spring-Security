package com.epam.esm.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * The type Jwt managing util.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Component
public class JwtManagingUtil {
    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.expiration.millis}")
    private long expirationMillis;

    /**
     * Create token string.
     *
     * @param subject the subject
     * @return the string
     */
    public String createToken(String subject) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date currentDate = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(expirationMillis + currentDate.getTime());
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return Jwts.builder()
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .setSubject(subject)
                .signWith(signatureAlgorithm, signingKey)
                .compact();
    }

    /**
     * Decode token claims.
     *
     * @param token the token
     * @return the claims
     */
    public Claims decodeToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Validate token boolean.
     *
     * @param token       the token
     * @param userDetails the user details
     * @return the boolean
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        boolean isTokenExpired = decodeToken(token)
                .getExpiration()
                .before(new Date());
        return (username.equals(userDetails.getUsername()) && !isTokenExpired);
    }

    /**
     * Gets username from token.
     *
     * @param token the token
     * @return the username from token
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
