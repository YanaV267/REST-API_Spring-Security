package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Pattern;

/**
 * The type Tag dto.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Data
@Relation(collectionRelation = "tags")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TagDto extends RepresentationModel<TagDto> {
    private long id;

    @Pattern(regexp = "[а-я\\p{Lower}_]{1,50}")
    private String name;
}
