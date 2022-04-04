package com.epam.esm.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ResourceBundle;

@Configuration
@EnableTransactionManagement
public class RepositoryConfig {
    private static final String DATABASE_PROPERTY_FILE = "database";
    private static final String DATABASE_URL_PROPERTY = "database.url";
    private static final String DATABASE_DRIVER_PROPERTY = "database.driver";
    private static final String DATABASE_USER_PROPERTY = "database.user";
    private static final String DATABASE_PASSWORD_PROPERTY = "database.password";
    private static final String DATABASE_POOL_SIZE = "pool.size";

    @Bean
    public DataSource dataSource() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DATABASE_PROPERTY_FILE);
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(resourceBundle.getString(DATABASE_DRIVER_PROPERTY));
        dataSource.setUrl(resourceBundle.getString(DATABASE_URL_PROPERTY));
        dataSource.setUsername(resourceBundle.getString(DATABASE_USER_PROPERTY));
        dataSource.setPassword(resourceBundle.getString(DATABASE_PASSWORD_PROPERTY));
        dataSource.setInitialSize(Integer.parseInt(resourceBundle.getString(DATABASE_POOL_SIZE)));
        return dataSource;
    }

    @Bean
    public TransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
