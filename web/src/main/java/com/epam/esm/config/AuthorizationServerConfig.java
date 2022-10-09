package com.epam.esm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * The type Oauth config.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Order(3)
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Value("${spring.security.oauth2.client.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.client-secret}")
    private String clientSecret;
    @Value("${jwt.secret.key}")
    private String privateKey;
    @Value("${jwt.secret.key}")
    private String publicKey;
    @Value("${spring.security.oauth2.authorisation.token-key-access}")
    private String tokenKeyAccess;
    @Value("${spring.security.oauth2.authorisation.check-token-access}")
    private String checkTokenAccess;
    @Value("${spring.security.oauth2.authorisation.scopes}")
    private String[] scopes;
    @Value("${spring.security.oauth2.authorisation.grant-types}")
    private String[] grantTypes;
    @Value("${jwt.expiration.seconds}")
    private int expirationSeconds;

    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Instantiates a new Authorization server config.
     *
     * @param authenticationManager the authentication manager
     * @param passwordEncoder       the password encoder
     */
    @Autowired
    public AuthorizationServerConfig(@Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager,
                                     @Qualifier("passwordEncoder") BCryptPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Token enhancer jwt access token converter.
     *
     * @return the jwt access token converter
     */
    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        return converter;
    }

    /**
     * Token store jwt token store.
     *
     * @return the jwt token store
     */
    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess(tokenKeyAccess)
                .checkTokenAccess(checkTokenAccess);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientId)
                .secret(passwordEncoder.encode(clientSecret))
                .scopes(scopes)
                .authorizedGrantTypes(grantTypes)
                .accessTokenValiditySeconds(expirationSeconds)
                .refreshTokenValiditySeconds(expirationSeconds);
    }
}
