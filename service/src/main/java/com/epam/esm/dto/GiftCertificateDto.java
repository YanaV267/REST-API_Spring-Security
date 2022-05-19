package com.epam.esm.dto;

import com.epam.esm.validation.OnAggregationCreateGroup;
import com.epam.esm.validation.OnCreateGroup;
import com.epam.esm.validation.OnSearchGroup;
import com.epam.esm.validation.OnUpdateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Gift certificate dto.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {
    @Min(value = 1, groups = {OnAggregationCreateGroup.class, OnUpdateGroup.class})
    private long id;

    @Pattern(regexp = "[А-Яа-я\\p{Alnum} _&]{2,25}", groups = OnCreateGroup.class)
    @Pattern(regexp = "[А-Яа-я\\p{Alnum} _&]{0,25}", groups = OnUpdateGroup.class)
    private String name;

    @Pattern(regexp = "[А-Яа-я\\p{Graph} ]{3,200}", groups = {OnCreateGroup.class, OnSearchGroup.class})
    @Pattern(regexp = "[А-Яа-я\\p{Graph} ]{0,200}", groups = {OnUpdateGroup.class, OnSearchGroup.class})
    private String description;

    @NotNull(groups = OnCreateGroup.class)
    @DecimalMin(value = "0.0")
    private BigDecimal price;

    @Min(value = 3, groups = OnCreateGroup.class)
    @Min(value = 0, groups = {OnUpdateGroup.class, OnSearchGroup.class})
    private int duration;

    @Null
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;

    @Null
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;

    @NotEmpty(groups = OnCreateGroup.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<@Valid TagDto> tags;

    /**
     * Instantiates a new Gift certificate dto.
     */
    @Tolerate
    public GiftCertificateDto() {
        tags = new LinkedHashSet<>();
    }
}
