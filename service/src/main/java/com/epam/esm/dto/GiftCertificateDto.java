package com.epam.esm.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

public class GiftCertificateDto {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private Set<TagDto> tags;

    public GiftCertificateDto() {
        tags = new LinkedHashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getCreateDate() {
        return String.valueOf(createDate);
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateDate() {
        return String.valueOf(lastUpdateDate);
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Set<TagDto> getTags() {
        return new LinkedHashSet<>(tags);
    }

    public void setTags(Set<TagDto> tags) {
        this.tags = new LinkedHashSet<>(tags);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GiftCertificateDto certificate = (GiftCertificateDto) o;
        if (name != null ? name.equals(certificate.name) : certificate.name == null) {
            return false;
        }
        if (price != null ? price.equals(certificate.price) : certificate.price == null) {
            return false;
        }
        if (description != null ? description.equals(certificate.description) : certificate.description == null) {
            return false;
        }
        if (createDate != null ? createDate.equals(certificate.createDate) : certificate.createDate == null) {
            return false;
        }
        if (lastUpdateDate != null ? lastUpdateDate.equals(certificate.lastUpdateDate) : certificate.lastUpdateDate == null) {
            return false;
        }
        return id == certificate.id && duration == certificate.duration;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 31 + Long.hashCode(id);
        result = result * 31 + (name != null ? name.hashCode() : 0);
        result = result * 31 + (description != null ? description.hashCode() : 0);
        result = result * 31 + (price != null ? price.hashCode() : 0);
        result = result * 31 + Integer.hashCode(duration);
        result = result * 31 + (createDate != null ? createDate.hashCode() : 0);
        result = result * 31 + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        return result;
    }

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
