package com.acme.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

class OrderDetail {

    @JsonProperty(value = "product")
    private Product product;

    @ApiModelProperty(notes = "Number of instances for the product in the order", example = "2")
    @JsonProperty(value = "product_quantity")
    private Long productQuantity;

    @ApiModelProperty(notes = "Price of the product, in USD and per unit, when it has been added to the order", example = "4")
    @JsonProperty(value = "product_price")
    private BigDecimal productPrice;

    OrderDetail(Product product, Long productQuantity, BigDecimal productPrice) {
        this.product = product;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }

    Long getProductQuantity() {
        return productQuantity;
    }

    BigDecimal getProductPrice() {
        return productPrice;
    }
}
