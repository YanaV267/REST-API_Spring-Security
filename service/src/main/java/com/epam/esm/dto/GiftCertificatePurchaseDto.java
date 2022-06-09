package com.epam.esm.dto;

import com.epam.esm.validation.OnAggregationCreateGroup;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * The type Gift certificate purchase dto.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Data
public class GiftCertificatePurchaseDto {
    @NotNull(groups = OnAggregationCreateGroup.class)
    private GiftCertificateDto certificate;

    @Min(value = 1, groups = OnAggregationCreateGroup.class)
    private int amount;

    /**
     * Instantiates a new Gift certificate purchase dto.
     *
     * @param certificate the certificate
     * @param amount      the amount
     */
    public GiftCertificatePurchaseDto(GiftCertificateDto certificate, int amount) {
        this.certificate = certificate;
        this.amount = amount;
    }
}
