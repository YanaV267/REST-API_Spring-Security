package com.epam.esm.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The type Order dto.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class OrderDto {
    private long id;
    private UserDto userDto;
    private BigDecimal cost;
    private LocalDateTime createDate;
    private GiftCertificateDto certificate;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("id=").append(id);
        sb.append(" , user=").append(userDto.toString());
        sb.append(" , cost=").append(cost);
        sb.append(" , createDate=").append(createDate);
        sb.append(" , certificates=").append(certificate.toString()).append("}");
        return sb.toString();
    }
}
