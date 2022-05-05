package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.NoDataFoundException;
import com.epam.esm.service.impl.UserServiceImpl;
import com.epam.esm.jwt.JwtResponseModel;
import com.epam.esm.jwt.JwtManagingUtil;
import com.epam.esm.validation.OnCreateGroup;
import com.epam.esm.validation.OnUpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.util.ParameterName.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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
public class UserController extends AbstractController<UserDto> {
    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final JwtManagingUtil jwtManagingUtil;

    /**
     * Instantiates a new User controller.
     *
     * @param userService the user service
     */
    @Autowired
    public UserController(UserServiceImpl userService, AuthenticationManager authenticationManager,
                          JwtManagingUtil jwtManagingUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtManagingUtil = jwtManagingUtil;
    }

    @Validated(OnUpdateGroup.class)
    @PostMapping("/signin")
    @ResponseStatus(OK)
    public JwtResponseModel signIn(@RequestBody UserDto user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userService.loadUserByUsername(user.getLogin());
        String token = jwtManagingUtil.createToken(userDetails.getUsername());
        return new JwtResponseModel(token);
    }

    @Validated(OnCreateGroup.class)
    @PostMapping("/signup")
    @ResponseStatus(OK)
    public String signUp(@RequestBody UserDto user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userService.loadUserByUsername(user.getLogin());
        return jwtManagingUtil.createToken(userDetails.getUsername());
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
    public CollectionModel<UserDto> retrieveAll(@RequestParam @Min(1) int page) {
        Set<UserDto> users = userService.findAll(page);
        int lastPage = userService.getLastPage();
        if (!users.isEmpty()) {
            addLinksToUsers(users);
            CollectionModel<UserDto> method = methodOn(UserController.class).retrieveAll(page);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(users, links);
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
    public CollectionModel<UserDto> retrieveAllWithOrders(@RequestParam @Min(1) int page) {
        Set<UserDto> users = userService.findAllWithOrders(page);
        int lastPage = userService.getLastPage();
        if (!users.isEmpty()) {
            addLinksToUsers(users);
            CollectionModel<UserDto> method = methodOn(UserController.class).retrieveAllWithOrders(page);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(users, links);
        } else {
            throw new NoDataFoundException(USERS, UserDto.class);
        }
    }

    /**
     * Retrieve by id user dto.
     *
     * @param id the id
     * @return the user dto
     */
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public UserDto retrieveById(@PathVariable @Min(1) long id) {
        Optional<UserDto> user = userService.findById(id);
        if (user.isPresent()) {
            Link link = linkTo(methodOn(UserController.class).retrieveById(id)).withSelfRel();
            user.get().add(link);
            return user.get();
        } else {
            throw new NoDataFoundException(ID, id, UserDto.class);
        }
    }

    /**
     * Retrieve with highest order cost set.
     *
     * @param page the page
     * @return the set
     */
    @GetMapping(value = "/highest-order-cost", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public CollectionModel<UserDto> retrieveWithHighestOrderCost(@RequestParam @Min(1) int page) {
        Set<UserDto> users = userService.findWithHighestOrderCost(page);
        int lastPage = userService.getLastPage();
        if (!users.isEmpty()) {
            addLinksToUsers(users);
            CollectionModel<UserDto> method = methodOn(UserController.class)
                    .retrieveWithHighestOrderCost(page);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(users, links);
        } else {
            throw new NoDataFoundException(HIGHEST_ORDER_COST, UserDto.class);
        }
    }

    /**
     * Retrieve with highest order cost most used tag collection model.
     *
     * @param page the page
     * @return the collection model
     */
    @GetMapping(value = "/most-used-tag", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public CollectionModel<UserDto> retrieveWithHighestOrderCostMostUsedTag(@RequestParam @Min(1) int page) {
        Set<UserDto> users = userService.findWithHighestOrderCostMostUsedTag(page);
        int lastPage = userService.getLastPage();
        if (!users.isEmpty()) {
            addLinksToUsers(users);
            CollectionModel<UserDto> method = methodOn(UserController.class)
                    .retrieveWithHighestOrderCost(page);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(users, links);
        } else {
            throw new NoDataFoundException(MOST_USED_TAG, UserDto.class);
        }
    }

    private void addLinksToUsers(Set<UserDto> users) {
        users.forEach(u -> {
            Link selfLink = linkTo(methodOn(UserController.class).retrieveById(u.getId())).withSelfRel();
            u.add(selfLink);
        });
    }
}
