package com.epam.esm.dto;

import com.epam.esm.validation.OnAggregationCreateGroup;
import com.epam.esm.validation.OnUpdateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Order dto.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class OrderDto extends RepresentationModel<OrderDto> {
    @Min(value = 1, groups = OnUpdateGroup.class)
    private long id;

    @NotNull(groups = OnAggregationCreateGroup.class)
    @Valid
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDto user;

    @Null(groups = OnAggregationCreateGroup.class)
    @DecimalMin(value = "0.0")
    private BigDecimal cost;

    @Null
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;

    @NotNull(groups = OnAggregationCreateGroup.class)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<@Valid GiftCertificatePurchaseDto> certificates;

    /**
     * Instantiates a new Order dto.
     */
    public OrderDto() {
        certificates = new LinkedHashSet<>();
    }
}
