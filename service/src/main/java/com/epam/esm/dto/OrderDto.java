package com.epam.esm.dto;

import com.epam.esm.validation.OnCreateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The type Order dto.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class OrderDto extends RepresentationModel<OrderDto> {
    private long id;

    @NotNull(groups = OnCreateGroup.class)
    @Valid
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDto user;

    @NotNull(groups = OnCreateGroup.class)
    @DecimalMin(value = "0.0")
    private BigDecimal cost;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;

    @NotNull(groups = OnCreateGroup.class)
    @Valid
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private GiftCertificateDto certificate;
}
