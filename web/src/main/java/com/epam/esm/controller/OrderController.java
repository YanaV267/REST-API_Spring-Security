package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.NoDataFoundException;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.util.ParameterName.*;
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
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public void create(@RequestBody OrderDto orderDto) {
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
    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public void update(@RequestBody OrderDto orderDto) {
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
    public Set<OrderDto> retrieveAll(@RequestParam @Min(1) int page) {
        Set<OrderDto> orders = orderService.findAll(page);
        if (!orders.isEmpty()) {
            return orders;
        } else {
            throw new NoDataFoundException(ORDERS, OrderDto.class);
        }
    }

    /**
     * Find by id order dto.
     *
     * @param id the id
     * @return the order dto
     */
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public OrderDto findById(@PathVariable @Min(1) long id) {
        Optional<OrderDto> order = orderService.findById(id);
        if (order.isPresent()) {
            return order.get();
        } else {
            throw new NoDataFoundException(ID, id, OrderDto.class);
        }
    }

    /**
     * Find all by user set.
     *
     * @param userId the user id
     * @param page   the page
     * @return the set
     */
    @GetMapping(value = "/user/{userId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public Set<OrderDto> findAllByUser(@PathVariable long userId, @RequestParam @Min(1) int page) {
        Set<OrderDto> orders = orderService.findAllByUser(page, userId);
        if (!orders.isEmpty()) {
            return orders;
        } else {
            throw new NoDataFoundException(ID_USER, userId, OrderDto.class);
        }
    }

    /**
     * Find by several parameters set.
     *
     * @param page     the page
     * @param orderDto the certificate dto
     * @return the set
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    public Set<OrderDto> findBySeveralParameters(@RequestParam @Min(1) int page,
                                                 @RequestParam OrderDto orderDto) {
        Set<OrderDto> orders = orderService.findBySeveralParameters(page, orderDto);
        if (!orders.isEmpty()) {
            return orders;
        } else {
            throw new NoDataFoundException(orderDto.toString(), OrderDto.class);
        }
    }
}
