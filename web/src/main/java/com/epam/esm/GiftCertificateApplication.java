package com.epam.esm;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Objects;

@SpringBootApplication
@PropertySource("classpath:/database/prod.properties")
public class GiftCertificateApplication {
    private static final String DATABASE_URL_PROPERTY = "db.url";
    private static final String DATABASE_DRIVER_PROPERTY = "db.driver";
    private static final String DATABASE_USER_PROPERTY = "db.user";
    private static final String DATABASE_PASSWORD_PROPERTY = "db.password";
    private static final String DATABASE_POOL_SIZE = "pool.size";
    @Autowired
    private Environment environment;

    @Bean
    public DataSource prodDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty(DATABASE_DRIVER_PROPERTY));
        dataSource.setUrl(environment.getProperty(DATABASE_URL_PROPERTY));
        dataSource.setUsername(environment.getProperty(DATABASE_USER_PROPERTY));
        dataSource.setPassword(environment.getProperty(DATABASE_PASSWORD_PROPERTY));
        dataSource.setInitialSize(Integer.parseInt(Objects.requireNonNull(environment.getProperty(DATABASE_POOL_SIZE))));
        return dataSource;
    }

    public static void main(String[] args) {
        SpringApplication.run(GiftCertificateApplication.class, args);
    }

}
