package com.acme.shop.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
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
