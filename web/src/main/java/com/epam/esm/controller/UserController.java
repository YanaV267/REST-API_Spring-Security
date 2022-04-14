package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.NoDataFoundException;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

import static com.epam.esm.util.ParameterName.ID;
import static com.epam.esm.util.ParameterName.USERS;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

/**
 * The type User controller.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@RestController
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
    public void delete(@PathVariable long id) {
        boolean isDeleted = userService.delete(id);
        if (!isDeleted) {
            throw new NoDataFoundException(ID, id, UserDto.class);
        }
    }

    /**
     * Retrieve all set.
     *
     * @return the set
     */
    @GetMapping
    @ResponseStatus(FOUND)
    public Set<UserDto> retrieveAll() {
        Set<UserDto> users = userService.findAll();
        if (!users.isEmpty()) {
            return users;
        } else {
            throw new NoDataFoundException(USERS, UserDto.class);
        }
    }

    /**
     * Retrieve all with orders set.
     *
     * @return the set
     */
    @GetMapping("/orders")
    @ResponseStatus(FOUND)
    public Set<UserDto> retrieveAllWithOrders() {
        Set<UserDto> users = userService.findAllWithOrders();
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
    @GetMapping("/{id}")
    @ResponseStatus(FOUND)
    public UserDto findById(@PathVariable long id) {
        Optional<UserDto> user = userService.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NoDataFoundException(ID, id, UserDto.class);
        }
    }
}
