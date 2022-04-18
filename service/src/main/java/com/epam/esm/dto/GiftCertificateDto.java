package com.epam.esm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * The type Gift certificate dto.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Data
@NoArgsConstructor
public class GiftCertificateDto {
    @Min(1)
    private long id;

    @Pattern(regexp = "[А-Яа-я\\p{Alnum} _]{1,25}")
    private String name;

    @Pattern(regexp = "[\\p{Graph} ]{3,200}")
    private String description;

    @DecimalMin("0.0")
    private BigDecimal price;

    @Min(3)
    private int duration;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;

    private Set<@Valid TagDto> tags;
}
