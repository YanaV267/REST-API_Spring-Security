package com.epam.esm.entity.converter;

import com.epam.esm.entity.User;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author YanaV
 * @project GiftCertificate
 */
@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<User.UserRole, String> {
    @Override
    public String convertToDatabaseColumn(User.UserRole role) {
        return role.toString().toLowerCase();
    }

    @Override
    public User.UserRole convertToEntityAttribute(String role) {
        return User.UserRole.valueOf(role.toUpperCase());
    }
}
