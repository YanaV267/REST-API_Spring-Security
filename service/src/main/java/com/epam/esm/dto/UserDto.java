package com.epam.esm.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

/**
 * The type User dto.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class UserDto {
    private long id;
    private String login;
    private String surname;
    private String name;
    private BigDecimal balance;
    private Set<OrderDto> orders;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("id=").append(id);
        sb.append(" , login='").append(login);
        sb.append("' , surname='").append(surname);
        sb.append("' , name='").append(name);
        sb.append("' , balance=").append(balance).append("}");
        return sb.toString();
    }
}
