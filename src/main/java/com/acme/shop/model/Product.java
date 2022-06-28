package com.acme.shop.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public class Product {

    private Long productId;

    private String productName;

    private BigDecimal unitPrice;

    public Product() {
    }

    public Product(Long productId, String productName, BigDecimal unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
    }

    public Product(String productName, BigDecimal unitPrice) {
        this.productName = productName;
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId.equals(product.productId) &&
                productName.equals(product.productName) &&
                unitPrice.equals(product.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, unitPrice);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
