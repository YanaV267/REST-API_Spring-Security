package com.epam.esm.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Set;

/**
 * The type User dto.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Data
@NoArgsConstructor
public class UserDto {
    @Min(1)
    private long id;

    @Pattern(regexp = "[\\p{Alnum}_]{1,30}")
    private String login;

    @Pattern(regexp = "[А-Яа-я\\p{Alpha}]{1,30}")
    private String surname;

    @Pattern(regexp = "[А-Яа-я\\p{Alpha}]{1,25}")
    private String name;

    @DecimalMin("0.0")
    private BigDecimal balance;

    private Set<@Valid OrderDto> orders;
}
