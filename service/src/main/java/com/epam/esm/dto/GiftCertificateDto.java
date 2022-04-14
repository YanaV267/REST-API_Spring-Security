package com.epam.esm.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * The type Gift certificate dto.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class GiftCertificateDto {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private Set<TagDto> tags;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("id=").append(id);
        sb.append(" , name='").append(name);
        sb.append("' , description='").append(description);
        sb.append("' , price=").append(price);
        sb.append(" , duration=").append(duration);
        sb.append(" , createDate='").append(createDate);
        sb.append("' , lastUpdateDate='").append(lastUpdateDate);
        sb.append("' , tags=").append(tags.toString()).append("}");
        return sb.toString();
    }
}
