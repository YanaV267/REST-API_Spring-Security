package com.epam.esm.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ResourceBundle;

@Configuration
@EnableTransactionManagement
public class RepositoryConfig {
    private static final String DATABASE_PROD_PROPERTY_FILE = "database/prod";
    private static final String DATABASE_URL_PROPERTY = "db.url";
    private static final String DATABASE_DRIVER_PROPERTY = "db.driver";
    private static final String DATABASE_USER_PROPERTY = "db.user";
    private static final String DATABASE_PASSWORD_PROPERTY = "db.password";
    private static final String DATABASE_POOL_SIZE = "pool.size";
    private static final String DATABASE_DATA_SCRIPT = "database/data_script.sql";
    private static final String DATABASE_CREATION_SCRIPT = "database/creation_script.sql";

    @Bean
    @Profile("prod")
    public DataSource prodDataSource() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DATABASE_PROD_PROPERTY_FILE);
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(resourceBundle.getString(DATABASE_DRIVER_PROPERTY));
        dataSource.setUrl(resourceBundle.getString(DATABASE_URL_PROPERTY));
        dataSource.setUsername(resourceBundle.getString(DATABASE_USER_PROPERTY));
        dataSource.setPassword(resourceBundle.getString(DATABASE_PASSWORD_PROPERTY));
        dataSource.setInitialSize(Integer.parseInt(resourceBundle.getString(DATABASE_POOL_SIZE)));
        return dataSource;
    }

    @Bean
    @Profile("dev")
    public DataSource devDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.H2)
                .addScript(DATABASE_CREATION_SCRIPT)
                .addScript(DATABASE_DATA_SCRIPT)
                .build();
    }

    @Bean
    @Profile("prod")
    public TransactionManager prodTransactionManager() {
        return new DataSourceTransactionManager(prodDataSource());
    }

    @Bean
    @Profile("dev")
    public TransactionManager devTransactionManager() {
        return new DataSourceTransactionManager(devDataSource());
    }
}
