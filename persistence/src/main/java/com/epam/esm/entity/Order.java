package com.epam.esm.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The type Order.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Entity
@Table(name = "orders")
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;
    private BigDecimal cost;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @OneToOne
    @JoinColumn(name = "id_certificate")
    private GiftCertificate certificate;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("id=").append(id);
        sb.append(" , user=").append(user.toString());
        sb.append(" , cost=").append(cost);
        sb.append(" , createDate=").append(createDate);
        sb.append(" , certificate=").append(certificate.toString()).append("}");
        return sb.toString();
    }

    /**
     * The type Order builder.
     */
    public static class OrderBuilder {
        private final Order order;

        /**
         * Instantiates a new Order builder.
         */
        public OrderBuilder() {
            order = new Order();
        }

        /**
         * Sets user.
         *
         * @param user the user
         * @return the user
         */
        public OrderBuilder setUser(User user) {
            order.user = user;
            return this;
        }

        /**
         * Sets cost.
         *
         * @param cost the cost
         * @return the cost
         */
        public OrderBuilder setCost(BigDecimal cost) {
            order.cost = cost;
            return this;
        }

        /**
         * Sets create date.
         *
         * @param createDate the create date
         * @return the create date
         */
        public OrderBuilder setCreateDate(LocalDateTime createDate) {
            order.createDate = createDate;
            return this;
        }

        /**
         * Sets certificate.
         *
         * @param certificate the certificate
         * @return the certificate
         */
        public OrderBuilder setCertificate(GiftCertificate certificate) {
            order.certificate = certificate;
            return this;
        }

        /**
         * Build order.
         *
         * @return the order
         */
        public Order build() {
            return order;
        }
    }
}
