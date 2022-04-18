package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The type Gift certificate application.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@SpringBootApplication
@EnableWebMvc
public class GiftCertificateApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(GiftCertificateApplication.class, args);
    }

}
