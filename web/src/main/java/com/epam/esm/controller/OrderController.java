package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.NoDataFoundException;
import com.epam.esm.service.OrderService;
import com.epam.esm.validation.OnAggregationCreateGroup;
import com.epam.esm.validation.OnUpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.util.ParameterName.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * The type Order controller.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@RestController
@Validated
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    /**
     * Instantiates a new Order controller.
     *
     * @param orderService the order service
     */
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Create.
     *
     * @param orderDto the order dto
     */
    @Validated(OnAggregationCreateGroup.class)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public void create(@RequestBody @Valid OrderDto orderDto) {
        boolean isCreated = orderService.create(orderDto);
        if (!isCreated) {
            throw new BadRequestException(OrderDto.class);
        }
    }

    /**
     * Update.
     *
     * @param orderDto the order dto
     */
    @Validated(OnUpdateGroup.class)
    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public void update(@RequestBody @Valid OrderDto orderDto) {
        boolean isUpdated = orderService.update(orderDto);
        if (!isUpdated) {
            throw new BadRequestException(OrderDto.class);
        }
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable @Min(1) long id) {
        boolean isDeleted = orderService.delete(id);
        if (!isDeleted) {
            throw new NoDataFoundException(ID, id, OrderDto.class);
        }
    }

    /**
     * Retrieve all set.
     *
     * @param page the page
     * @return the set
     */
    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public CollectionModel<OrderDto> retrieveAll(@RequestParam @Min(1) int page) {
        Set<OrderDto> orders = orderService.findAll(page);
        if (!orders.isEmpty()) {
            addLinksToOrders(orders);
            Link link = linkTo(methodOn(OrderController.class).retrieveAll(page)).withSelfRel();
            return CollectionModel.of(orders, link);
        } else {
            throw new NoDataFoundException(ORDERS, OrderDto.class);
        }
    }

    /**
     * Retrieve by id order dto.
     *
     * @param id the id
     * @return the order dto
     */
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public OrderDto retrieveById(@PathVariable @Min(1) long id) {
        Optional<OrderDto> order = orderService.findById(id);
        if (order.isPresent()) {
            Link link = linkTo(methodOn(OrderController.class).retrieveById(id)).withSelfRel();
            order.get().add(link);
            return order.get();
        } else {
            throw new NoDataFoundException(ID, id, OrderDto.class);
        }
    }

    /**
     * Retrieve all by user set.
     *
     * @param userId the user id
     * @param page   the page
     * @return the set
     */
    @GetMapping(value = "/user/{userId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public CollectionModel<OrderDto> retrieveAllByUser(@PathVariable @Min(1) long userId,
                                                       @RequestParam @Min(1) int page) {
        Set<OrderDto> orders = orderService.findAllByUser(page, userId);
        if (!orders.isEmpty()) {
            addLinksToOrders(orders);
            Link link = linkTo(methodOn(OrderController.class).retrieveAllByUser(userId, page)).withSelfRel();
            return CollectionModel.of(orders, link);
        } else {
            throw new NoDataFoundException(ID_USER, userId, OrderDto.class);
        }
    }

    /**
     * Retrieve by several parameters set.
     *
     * @param page     the page
     * @param orderDto the certificate dto
     * @return the set
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public CollectionModel<OrderDto> retrieveBySeveralParameters(@RequestParam @Min(1) int page,
                                                                 @RequestParam @Valid OrderDto orderDto) {
        Set<OrderDto> orders = orderService.findBySeveralParameters(page, orderDto);
        if (!orders.isEmpty()) {
            addLinksToOrders(orders);
            Link link = linkTo(methodOn(OrderController.class)
                    .retrieveBySeveralParameters(page, orderDto)).withSelfRel();
            return CollectionModel.of(orders, link);
        } else {
            throw new NoDataFoundException(orderDto.toString(), OrderDto.class);
        }
    }

    private void addLinksToOrders(Set<OrderDto> orders) {
        orders.forEach(o -> {
            Link selfLink = linkTo(methodOn(OrderController.class).retrieveById(o.getId())).withSelfRel();
            o.add(selfLink);
        });
    }
}
