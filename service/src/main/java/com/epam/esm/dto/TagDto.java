package com.epam.esm.dto;

/**
 * The type Tag dto.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public class TagDto {
    private long id;
    private String name;

    /**
     * Instantiates a new Tag dto.
     */
    public TagDto() {

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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TagDto tag = (TagDto) o;
        return id == tag.id && (name != null ? name.equals(tag.name) : tag.name == null);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 31 + Long.hashCode(id);
        result = result * 31 + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("id=").append(id);
        sb.append(" , name='").append(name).append("'}");
        return sb.toString();
    }
}
