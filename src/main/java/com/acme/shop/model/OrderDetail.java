package com.acme.shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class OrderDetail {

    @JsonProperty(value = "order")
    private Order order;

    @JsonProperty(value = "product")
    private Product product;

    @ApiModelProperty(notes = "Number of instances for the product in the order", example = "2")
    @JsonProperty(value = "product_quantity")
    private Long productQuantity;

    @ApiModelProperty(notes = "Price of the product, in USD and per unit, when it has been added to the order", example = "4")
    @JsonProperty(value = "product_price")
    private BigDecimal productPrice;

    public OrderDetail(Order order, Product product, Long productQuantity, BigDecimal productPrice) {
        this.order = order;
        this.product = product;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }
}
