package com.epam.esm.dto;

import com.epam.esm.entity.Order;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type User dto.
 *
 * @author YanaV
 * @project GiftCertificate
 */
public class UserDto {
    private long id;
    private String login;
    private String surname;
    private String name;
    private BigDecimal balance;
    private Set<OrderDto> orders;

    /**
     * Instantiates a new User dto.
     */
    public UserDto() {
        orders = new LinkedHashSet<>();
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
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Gets surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
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
     * Gets balance.
     *
     * @return the balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Gets orders.
     *
     * @return the orders
     */
    public Set<OrderDto> getOrders() {
        return orders;
    }

    /**
     * Sets orders.
     *
     * @param orders the orders
     */
    public void setOrders(Set<OrderDto> orders) {
        this.orders = orders;
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
     * Sets login.
     *
     * @param login the login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Sets surname.
     *
     * @param surname the surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets balance.
     *
     * @param balance the balance
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        if (login != null ? login.equals(userDto.login) : userDto.login == null) {
            return false;
        }
        if (surname != null ? surname.equals(userDto.surname) : userDto.surname == null) {
            return false;
        }
        if (name != null ? name.equals(userDto.name) : userDto.name == null) {
            return false;
        }
        return balance != null ? balance.equals(userDto.balance) : userDto.balance == null;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 31 + Long.hashCode(id);
        result = result * 31 + (login != null ? login.hashCode() : 0);
        result = result * 31 + (surname != null ? surname.hashCode() : 0);
        result = result * 31 + (name != null ? name.hashCode() : 0);
        result = result * 31 + (balance != null ? balance.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("id=").append(id);
        sb.append(" , login='").append(login);
        sb.append("' , surname='").append(surname);
        sb.append("' , name='").append(name);
        sb.append("' , balance=").append(balance).append("}");
        return sb.toString();
    }
}
