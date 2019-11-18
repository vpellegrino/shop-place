package com.acme.shop.model;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderDetail {

    private Product product;

    private Long productQuantity;

    private BigDecimal productPrice;

    public OrderDetail() {
    }

    public OrderDetail(Product product, Long productQuantity, BigDecimal productPrice) {
        this.product = product;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        return product.equals(that.product) &&
                productQuantity.equals(that.productQuantity) &&
                productPrice.equals(that.productPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, productQuantity, productPrice);
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "product=" + product +
                ", productQuantity=" + productQuantity +
                ", productPrice=" + productPrice +
                '}';
    }

}
