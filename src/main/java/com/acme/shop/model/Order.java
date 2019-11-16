package com.acme.shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel(description = "An order representation")
public class Order {

    @ApiModelProperty(notes = "Order's identifier", example = "789")
    @JsonProperty(value = "order_id")
    private BigDecimal orderId;

    @ApiModelProperty(notes = "Buyer's e-mail address", example = "donald@acme.com")
    @JsonProperty(value = "buyer_email")
    private String buyerEmail;

    @ApiModelProperty(notes = "Order's date", example = "2013-07-10 04:28:30")
    @JsonProperty(value = "product_id")
    private Date orderDate;

    @JsonProperty(value = "involved_products")
    private List<OrderDetail> involvedProductList;

    public Order(BigDecimal orderId, String buyerEmail, List<OrderDetail> involvedProductList) {
        this.orderId = orderId;
        this.buyerEmail = buyerEmail;
        this.orderDate = new Date();
        this.involvedProductList = involvedProductList;
    }
}
