package com.epam.esm.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * The type Repository config.
 */
@Configuration
@PropertySource("classpath:/database/prod.properties")
@ComponentScan("com.epam.esm")
@EnableTransactionManagement
public class RepositoryConfig {
    private static final String DATABASE_URL_PROPERTY = "db.url";
    private static final String DATABASE_DRIVER_PROPERTY = "db.driver";
    private static final String DATABASE_USER_PROPERTY = "db.user";
    private static final String DATABASE_PASSWORD_PROPERTY = "db.password";
    private static final String DATABASE_POOL_SIZE = "pool.size";
    private static final String DATABASE_DATA_SCRIPT = "database/data_script.sql";
    private static final String DATABASE_CREATION_SCRIPT = "database/creation_script.sql";
    @Autowired
    private Environment environment;

    /**
     * Prod data source data source.
     *
     * @return the data source
     */
    @Bean
    @Profile("prod")
    public DataSource prodDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty(DATABASE_DRIVER_PROPERTY));
        dataSource.setUrl(environment.getProperty(DATABASE_URL_PROPERTY));
        dataSource.setUsername(environment.getProperty(DATABASE_USER_PROPERTY));
        dataSource.setPassword(environment.getProperty(DATABASE_PASSWORD_PROPERTY));
        dataSource.setInitialSize(Integer.parseInt(Objects.requireNonNull(environment.getProperty(DATABASE_POOL_SIZE))));
        return dataSource;
    }

    /**
     * Dev data source data source.
     *
     * @return the data source
     */
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

    /**
     * Prod transaction manager transaction manager.
     *
     * @return the transaction manager
     */
    @Bean
    @Profile("prod")
    public TransactionManager prodTransactionManager() {
        return new DataSourceTransactionManager(prodDataSource());
    }

    /**
     * Dev transaction manager transaction manager.
     *
     * @return the transaction manager
     */
    @Bean
    @Profile("dev")
    public TransactionManager devTransactionManager() {
        return new DataSourceTransactionManager(devDataSource());
    }
}
