package com.epam.esm.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Tag dto.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class TagDto {
    private long id;
    private String name;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("id=").append(id);
        sb.append(" , name='").append(name).append("'}");
        return sb.toString();
    }
}
