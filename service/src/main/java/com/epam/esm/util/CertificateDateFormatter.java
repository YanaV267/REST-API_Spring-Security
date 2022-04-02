package com.epam.esm.util;

import com.epam.esm.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CertificateDateFormatter {
    private static final Logger LOGGER = LogManager.getLogger(CertificateDateFormatter.class);
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public Date format(String date) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return dateFormat.parse(date);
        } catch (ParseException exception) {
            LOGGER.error("Error has occurred while parsing date into ISO-8601 format: " + exception);
            throw new ServiceException("Error has occurred while parsing date into ISO-8601 format: ", exception);
        }
    }
}
