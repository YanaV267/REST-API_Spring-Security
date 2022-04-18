package com.epam.esm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 * The type Tag dto.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Data
@NoArgsConstructor
public class TagDto {
    @Min(1)
    private long id;

    @Pattern(regexp = "[а-я\\p{Lower} _]{2,50}")
    private String name;
}
