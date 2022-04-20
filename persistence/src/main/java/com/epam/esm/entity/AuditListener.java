package com.epam.esm.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * @author YanaV
 * @project GiftCertificate
 */
class AuditListener {
     static final Logger LOGGER = LogManager.getLogger(AuditListener.class);
    static final String INSERT_OPERATION = "INSERT";
    static final String UPDATE_OPERATION = "UPDATE";
    static final String DELETE_OPERATION = "DELETE";
    static LocalDateTime auditDateTime;

    @PrePersist
    public void onPrePersist(Object object) {
        audit(object, INSERT_OPERATION);
    }

    @PreUpdate
    public void onPreUpdate(Object object) {
        audit(object, UPDATE_OPERATION);
    }

    @PreRemove
    public void onPreRemove(Object object) {
        audit(object, DELETE_OPERATION);
    }

    static void audit(Object object, String operation) {
        auditDateTime = LocalDateTime.now();
        LOGGER.info("Class: {}", object.getClass());
        LOGGER.info("Entity: {}", object);
        LOGGER.info("Operation: {}", operation);
        LOGGER.info("Date & time: {}", auditDateTime);
    }
}
