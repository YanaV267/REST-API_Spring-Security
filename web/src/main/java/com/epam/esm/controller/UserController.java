package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.NoDataFoundException;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.util.ParameterName.*;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * The type User controller.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@RestController
@Validated
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    /**
     * Instantiates a new User controller.
     *
     * @param userService the user service
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable @Min(1) long id) {
        boolean isDeleted = userService.delete(id);
        if (!isDeleted) {
            throw new NoDataFoundException(ID, id, UserDto.class);
        }
    }

    /**
     * Retrieve all set.
     *
     * @param page the page
     * @return the set
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public Set<UserDto> retrieveAll(@RequestParam @Min(1) int page) {
        Set<UserDto> users = userService.findAll(page);
        if (!users.isEmpty()) {
            return users;
        } else {
            throw new NoDataFoundException(USERS, UserDto.class);
        }
    }

    /**
     * Retrieve all with orders set.
     *
     * @param page the page
     * @return the set
     */
    @GetMapping(value = "/orders", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public Set<UserDto> retrieveAllWithOrders(@RequestParam @Min(1) int page) {
        Set<UserDto> users = userService.findAllWithOrders(page);
        if (!users.isEmpty()) {
            return users;
        } else {
            throw new NoDataFoundException(USERS, UserDto.class);
        }
    }

    /**
     * Find by id user dto.
     *
     * @param id the id
     * @return the user dto
     */
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public UserDto retrieveById(@PathVariable @Min(1) long id) {
        Optional<UserDto> user = userService.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NoDataFoundException(ID, id, UserDto.class);
        }
    }

    /**
     * Find with highest order cost set.
     *
     * @param page the page
     * @return the set
     */
    @GetMapping(value = "/highest-order-cost", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public Set<UserDto> retrieveWithHighestOrderCost(@RequestParam @Min(1) int page) {
        Set<UserDto> orders = userService.findWithHighestOrderCost(page);
        if (!orders.isEmpty()) {
            return orders;
        } else {
            throw new NoDataFoundException(HIGHEST_ORDER_COST, UserDto.class);
        }
    }
}
