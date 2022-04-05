package com.epam.esm.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Certificate date formatter.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Component
public class CertificateDateFormatter {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    /**
     * Format local date time.
     *
     * @param date the date
     * @return the local date time
     */
    public LocalDateTime format(String date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDateTime.parse(date, dateFormatter);
    }
}
