package com.acme.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel(description = "A product representation")
public class ProductDTO {

    @ApiModelProperty(notes = "The product identifier", example = "12345", readOnly = true)
    @JsonProperty(value = "product_id")
    private Long productId;

    @ApiModelProperty(notes = "The product name", example = "Gel Shampoo")
    @JsonProperty(value = "product_name")
    private String productName;

    @ApiModelProperty(notes = "The product price, per unit, in USD", example = "5")
    @JsonProperty(value = "unit_price")
    private BigDecimal unitPrice;

    public ProductDTO() {
    }

    public ProductDTO(Long productId, String productName, BigDecimal unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public static class Validity {
        public static void checkValidity(ProductDTO product) {
            if (product == null || product.getProductName() == null || product.getUnitPrice() == null) {
                throw new IllegalArgumentException("Product is missing something. Check the input!");
            }
        }
    }

}
