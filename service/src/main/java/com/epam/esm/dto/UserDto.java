package com.epam.esm.dto;

import com.epam.esm.validation.OnAggregationCreateGroup;
import com.epam.esm.validation.OnCreateGroup;
import com.epam.esm.validation.OnUpdateGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type User dto.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class UserDto extends RepresentationModel<UserDto> {
    @Min(value = 1, groups = OnAggregationCreateGroup.class)
    private long id;

    @Pattern(regexp = "[\\p{Alnum}_]{1,30}", groups = {OnCreateGroup.class, OnUpdateGroup.class})
    private String login;

    @Pattern(regexp = "[\\p{Alnum}_]{1,60}", groups = {OnCreateGroup.class, OnUpdateGroup.class})
    private String password;

    @NotNull(groups = OnCreateGroup.class)
    @Pattern(regexp = "[А-Яа-я\\p{Alpha}]{1,30}", groups = OnCreateGroup.class)
    private String surname;

    @NotNull(groups = OnCreateGroup.class)
    @Pattern(regexp = "[А-Яа-я\\p{Alpha}]{1,25}", groups = OnCreateGroup.class)
    private String name;

    @Null(groups = OnUpdateGroup.class)
    @NotNull(groups = OnCreateGroup.class)
    @DecimalMin("0.0")
    private BigDecimal balance;

    @Size
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<@Valid OrderDto> orders;

    /**
     * Instantiates a new User dto.
     */
    @Tolerate
    public UserDto() {
        orders = new LinkedHashSet<>();
    }
}
