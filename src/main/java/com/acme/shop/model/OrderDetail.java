package com.acme.shop.model;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderDetail {

    private Long productId;

    private Long productQuantity;

    private BigDecimal productPrice;

    private String productName;

    public OrderDetail() {
    }

    public OrderDetail(Long productId, String productName, Long productQuantity, BigDecimal productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetail that = (OrderDetail) o;
        return productId.equals(that.productId) &&
                productQuantity.equals(that.productQuantity) &&
                productPrice.equals(that.productPrice) &&
                Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productQuantity, productPrice, productName);
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "productId=" + productId +
                ", productQuantity=" + productQuantity +
                ", productPrice=" + productPrice +
                ", productName='" + productName + '\'' +
                '}';
    }

}
