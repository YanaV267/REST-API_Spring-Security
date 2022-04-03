package com.epam.esm.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

@Configuration
public class RepositoryConfig {
    private static final Logger LOGGER = LogManager.getLogger(RepositoryConfig.class);
    private static final String DATABASE_PROPERTY_FILE = "database";
    private static final String DATABASE_URL_PROPERTY = "url";
    private static final String DATABASE_DRIVER_PROPERTY = "driver";
    private static final String DATABASE_USER_PROPERTY = "user";
    private static final String DATABASE_PASSWORD_PROPERTY = "password";

    @Bean
    public DataSource dataSource() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DATABASE_PROPERTY_FILE);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        if (resourceBundle.containsKey(DATABASE_DRIVER_PROPERTY)) {
            dataSource.setDriverClassName(resourceBundle.getString(DATABASE_DRIVER_PROPERTY));
        } else {
            LOGGER.fatal("Error of retrieving driver property value");
            throw new RuntimeException("Error of retrieving driver property value");
        }
        if (resourceBundle.containsKey(DATABASE_URL_PROPERTY)) {
            dataSource.setUrl(resourceBundle.getString(DATABASE_URL_PROPERTY));
        } else {
            LOGGER.fatal("Error of retrieving url property value");
            throw new RuntimeException("Error of retrieving url property value");
        }
        if (resourceBundle.containsKey(DATABASE_USER_PROPERTY)) {
            dataSource.setUsername(resourceBundle.getString(DATABASE_USER_PROPERTY));
        } else {
            LOGGER.fatal("Error of retrieving user property value");
            throw new RuntimeException("Error of retrieving user property value");
        }
        if (resourceBundle.containsKey(DATABASE_PASSWORD_PROPERTY)) {
            dataSource.setPassword(resourceBundle.getString(DATABASE_PASSWORD_PROPERTY));
        } else {
            LOGGER.fatal("Error of retrieving password property value");
            throw new RuntimeException("Error of retrieving password property value");
        }
        return dataSource;
    }
}
