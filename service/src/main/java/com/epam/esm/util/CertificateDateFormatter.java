package com.epam.esm.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CertificateDateFormatter {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public LocalDateTime format(String date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDateTime.parse(date, dateFormatter);
    }
}
