package com.epam.esm.config;

import com.epam.esm.connection.ConnectionPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    @Bean
    public ConnectionPool connectionPool() {
        return new ConnectionPool();
    }
}
