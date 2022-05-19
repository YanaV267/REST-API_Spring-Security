package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.NoDataFoundException;
import com.epam.esm.service.OrderService;
import com.epam.esm.validation.OnAggregationCreateGroup;
import com.epam.esm.validation.OnSearchGroup;
import com.epam.esm.validation.OnUpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
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
public class OrderController extends AbstractController<OrderDto> {
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
    @Secured("ROLE_USER")
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
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
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
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
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
    @Secured("ROLE_ADMIN")
    public CollectionModel<OrderDto> retrieveAll(@RequestParam @Min(1) int page) {
        Set<OrderDto> orders = orderService.findAll(page);
        int lastPage = orderService.getLastPage();
        if (!orders.isEmpty()) {
            addLinksToOrders(orders);
            CollectionModel<OrderDto> method = methodOn(OrderController.class).retrieveAll(page);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(orders, links);
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
    @Secured("ROLE_ADMIN")
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
        int lastPage = orderService.getLastPage();
        if (!orders.isEmpty()) {
            addLinksToOrders(orders);
            CollectionModel<OrderDto> method = methodOn(OrderController.class).retrieveAllByUser(userId, page);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(orders, links);
        } else {
            throw new NoDataFoundException(ID_USER, userId, OrderDto.class);
        }
    }

    /**
     * Retrieve by several parameters set.
     *
     * @param page           the page
     * @param orderDto       the certificate dto
     * @param userId         the user id
     * @param certificateIds the certificate ids
     * @return the set
     */
    @Validated(OnSearchGroup.class)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(FOUND)
    @Secured("ROLE_ADMIN")
    public CollectionModel<OrderDto> retrieveBySeveralParameters(
            @RequestParam @Min(1) int page,
            @Valid OrderDto orderDto,
            @RequestParam(value = "user_id", required = false)
            @Min(1) Long userId,
            @RequestParam(value = "certificate", required = false)
                    List<@Min(1) Integer> certificateIds) {
        Set<OrderDto> orders = orderService.findBySeveralParameters(page, orderDto, userId, certificateIds);
        int lastPage = orderService.getLastPage();
        if (!orders.isEmpty()) {
            addLinksToOrders(orders);
            CollectionModel<OrderDto> method = methodOn(OrderController.class)
                    .retrieveBySeveralParameters(page, orderDto, userId, certificateIds);
            List<Link> links = addPagesLinks(method, page, lastPage);
            return CollectionModel.of(orders, links);
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
