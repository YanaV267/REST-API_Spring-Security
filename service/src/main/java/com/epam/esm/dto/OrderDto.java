package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The type Order dto.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public class OrderDto {
    private long id;
    private UserDto userDto;
    private BigDecimal cost;
    private LocalDateTime createDate;
    private GiftCertificate certificate;

    /**
     * Instantiates a new Order dto.
     */
    public OrderDto() {
        certificate = new GiftCertificate();
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public UserDto getUser() {
        return userDto;
    }

    /**
     * Sets user.
     *
     * @param userDto the user dto
     */
    public void setUser(UserDto userDto) {
        this.userDto = userDto;
    }

    /**
     * Gets cost.
     *
     * @return the cost
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * Sets cost.
     *
     * @param cost the cost
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * Gets create date.
     *
     * @return the create date
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets create date.
     *
     * @param createDate the create date
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets certificate.
     *
     * @return the certificate
     */
    public GiftCertificate getCertificate() {
        return certificate;
    }

    /**
     * Sets certificate.
     *
     * @param certificate the certificate
     */
    public void setCertificate(GiftCertificate certificate) {
        this.certificate = certificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        if (userDto != null ? userDto.equals(orderDto.userDto) : orderDto.userDto == null) {
            return false;
        }
        if (cost != null ? cost.equals(orderDto.cost) : orderDto.cost == null) {
            return false;
        }
        if (createDate != null ? createDate.equals(orderDto.createDate) : orderDto.createDate == null) {
            return false;
        }
        if (certificate != null ? certificate.equals(orderDto.certificate) : orderDto.certificate == null) {
            return false;
        }
        return id == orderDto.id;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 31 + Long.hashCode(id);
        result = result * 31 + (userDto != null ? userDto.hashCode() : 0);
        result = result * 31 + (cost != null ? cost.hashCode() : 0);
        result = result * 31 + (createDate != null ? createDate.hashCode() : 0);
        result = result * 31 + (certificate != null ? certificate.hashCode() : 0);
        return result;
    }

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
