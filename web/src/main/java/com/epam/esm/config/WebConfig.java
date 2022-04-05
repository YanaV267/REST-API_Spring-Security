package com.epam.esm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The type Web config.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.epam.esm")
public class WebConfig {

}
