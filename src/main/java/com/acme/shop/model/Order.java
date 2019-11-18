package com.acme.shop.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Order {

    private Long orderId;

    private String buyerEmail;

    private Date orderDate;

    private List<OrderDetail> involvedProductList;

    public Order() {
        this.orderDate = new Date();
    }

    public Order(Long orderId, String buyerEmail, Date orderDate, List<OrderDetail> involvedProductList) {
        this.orderId = orderId;
        this.buyerEmail = buyerEmail;
        this.orderDate = orderDate;
        this.involvedProductList = involvedProductList;
    }

    public Order(String buyerEmail, List<OrderDetail> involvedProductList) {
        this.buyerEmail = buyerEmail;
        this.orderDate = new Date();
        this.involvedProductList = involvedProductList;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderDetail> getInvolvedProductList() {
        return involvedProductList;
    }

    public void setInvolvedProductList(List<OrderDetail> involvedProductList) {
        this.involvedProductList = involvedProductList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId.equals(order.orderId) &&
                buyerEmail.equals(order.buyerEmail) &&
                orderDate.equals(order.orderDate) &&
                involvedProductList.equals(order.involvedProductList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, buyerEmail, orderDate, involvedProductList);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", buyerEmail='" + buyerEmail + '\'' +
                ", orderDate=" + orderDate +
                ", involvedProductList=" + involvedProductList +
                '}';
    }

}
