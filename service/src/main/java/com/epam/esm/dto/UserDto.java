package com.epam.esm.dto;

import com.epam.esm.validation.OnAggregationCreateGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

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
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserDto extends RepresentationModel<UserDto> {
    @Min(value = 1, groups = OnAggregationCreateGroup.class)
    private long id;

    @Pattern(regexp = "[\\p{Alnum}_]{1,30}")
    private String login;

    @Pattern(regexp = "[А-Яа-я\\p{Alpha}]{1,30}")
    private String surname;

    @Pattern(regexp = "[А-Яа-я\\p{Alpha}]{1,25}")
    private String name;

    @DecimalMin("0.0")
    private BigDecimal balance;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<@Valid OrderDto> orders;
}
