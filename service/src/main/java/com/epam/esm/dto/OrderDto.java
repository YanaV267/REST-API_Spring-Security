package com.epam.esm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
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
@NoArgsConstructor
public class OrderDto {
    @Min(1)
    private long id;

    @NotNull
    @Valid
    private UserDto userDto;

    @DecimalMin("0.0")
    private BigDecimal cost;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;

    @NotNull
    @Valid
    private GiftCertificateDto certificateDto;
}
