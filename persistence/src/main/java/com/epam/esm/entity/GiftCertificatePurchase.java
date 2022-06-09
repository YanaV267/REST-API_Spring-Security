package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Tolerate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.EmbeddedId;
import javax.persistence.MapsId;
import javax.persistence.Embeddable;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.io.Serializable;

/**
 * The type Gift certificate purchase.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Entity
@Table(name = "certificate_purchase")
@Data
public class GiftCertificatePurchase implements Serializable {
    @EmbeddedId
    private OrderPurchaseKey id;
    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "id_order")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Order order;

    @ManyToOne
    @MapsId("certificateId")
    @JoinColumn(name = "id_certificate")
    private GiftCertificate certificate;

    private int amount;

    /**
     * Instantiates a new Gift certificate purchase.
     *
     * @param certificate the certificate
     * @param amount      the amount
     */
    @Tolerate
    public GiftCertificatePurchase(GiftCertificate certificate, int amount) {
        id = new OrderPurchaseKey();
        order = new Order();
        this.certificate = certificate;
        this.amount = amount;
    }

    /**
     * Instantiates a new Gift certificate purchase.
     *
     * @param certificateId the certificate id
     */
    @Tolerate
    public GiftCertificatePurchase(int certificateId) {
        id = new OrderPurchaseKey();
        order = new Order();
        certificate = new GiftCertificate(certificateId);
    }

    /**
     * The type Order purchase key.
     */
    @Embeddable
    @Data
    public static class OrderPurchaseKey implements Serializable {
        @Column(name = "id_order")
        private long orderId;

        @Column(name = "id_certificate")
        private long certificateId;
    }
}
