package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;

/**
 * The type Tag.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Entity
@Table(name = "tags")
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    /**
     * Instantiates a new Tag.
     *
     * @param name the name
     */
    public Tag(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("id=").append(id);
        sb.append(" , name='").append(name).append("'}");
        return sb.toString();
    }
}
