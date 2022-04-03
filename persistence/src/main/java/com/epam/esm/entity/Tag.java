package com.epam.esm.entity;

public class Tag {
    private long id;
    private String name;

    public Tag() {

    }

    public Tag(String name) {
        this.name = name;
    }

    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Tag tag = (Tag) o;
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
