package com.acme.shop.controller;

import com.acme.shop.model.ApiError;
import com.acme.shop.model.Order;
import io.swagger.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/")
@Api(value = "order-api", tags = "Orders API")
public class OrderController {

    private static final String ACCEPTED_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @PostMapping(path = "orders", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Places an order", nickname = "placeOrder", response = BigDecimal.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The identifier of the created order", response = BigDecimal.class),
            @ApiResponse(code = 400, message = "Invalid input", response = ApiError.class),
            @ApiResponse(code = 500, message = "An unexpected error occurred", response = ApiError.class)})
    @ResponseStatus(HttpStatus.CREATED)
    public BigDecimal placeOrder(@RequestBody Order order) {
        return null;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @GetMapping(path = "orders", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieves the list of all orders within a given time period, specified by two (inclusive) dates",
            nickname = "getOrderListBetweenDates", response = Order.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The list of all orders within a given time period", response = Order.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "End date must not be before the start date", response = ApiError.class),
            @ApiResponse(code = 500, message = "An unexpected error occurred", response = ApiError.class)})
    public List<Order> getOrderListBetweenDates(
            @ApiParam(value = "Inclusive start date", required = true, example = "2012-07-10 14:58:00")
            @RequestParam(value = "start") @DateTimeFormat(pattern = ACCEPTED_DATETIME_FORMAT) Date startDate,

            @ApiParam(value = "Inclusive end date", example = "2014-07-10 00:00:00")
            @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = ACCEPTED_DATETIME_FORMAT) Optional<Date> endDate) {
        return null;
    }

}
