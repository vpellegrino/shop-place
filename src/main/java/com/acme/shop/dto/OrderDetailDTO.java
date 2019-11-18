package com.acme.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel(description = "An order detail representation")
public class OrderDetailDTO {

    @ApiModelProperty(notes = "Product name", example = "Crackers", readOnly = true)
    @JsonProperty(value = "product_name")
    private String productName;

    @ApiModelProperty(notes = "Product identifier", example = "1234")
    @JsonProperty(value = "product_id")
    private Long productId;

    @ApiModelProperty(notes = "Number of instances for the product in the order", example = "2")
    @JsonProperty(value = "product_quantity")
    private Long productQuantity;

    @ApiModelProperty(notes = "Price of the product, in USD and per unit, when it has been added to the order", example = "4", readOnly = true)
    @JsonProperty(value = "unit_price")
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

    BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public static class Validity {
        public static void checkDetailValidity(OrderDetailDTO detail) {
            if (detail == null || detail.getProductId() == null || detail.getProductQuantity() == null) {
                throw new IllegalArgumentException("Order detail is missing something. Check the input!");
            }
        }
    }

}
