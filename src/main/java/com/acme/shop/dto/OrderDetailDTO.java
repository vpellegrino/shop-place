package com.acme.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class OrderDetailDTO {

    @ApiModelProperty(notes = "Product name", example = "Crackers")
    @JsonProperty(value = "product")
    private String productName;

    @ApiModelProperty(notes = "Product identifier", example = "1234")
    @JsonProperty(value = "product_id")
    private Long productId;

    @ApiModelProperty(notes = "Number of instances for the product in the order", example = "2")
    @JsonProperty(value = "product_quantity")
    private Long productQuantity;

    @ApiModelProperty(notes = "Price of the product, in USD and per unit, when it has been added to the order", example = "4")
    @JsonProperty(value = "product_price")
    private BigDecimal productPrice;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(Long productId, String productName, Long productQuantity, BigDecimal productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
}
