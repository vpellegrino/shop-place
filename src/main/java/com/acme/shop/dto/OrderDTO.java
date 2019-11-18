package com.acme.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel(description = "An order representation")
public class OrderDTO {

    @ApiModelProperty(notes = "Order's identifier", example = "789", readOnly = true)
    @JsonProperty(value = "order_id")
    private Long orderId;

    @ApiModelProperty(notes = "Buyer's e-mail address", example = "donald@acme.com")
    @JsonProperty(value = "buyer_email")
    private String buyerEmail;

    @ApiModelProperty(notes = "Order's date", example = "2013-07-10 04:28:30", readOnly = true)
    @JsonProperty(value = "order_date")
    private Date orderDate;

    @JsonProperty(value = "involved_products")
    private List<OrderDetailDTO> involvedProductList;

    @ApiModelProperty(notes = "Total value of the order", example = "8", readOnly = true)
    @JsonProperty(value = "total_value")
    public BigDecimal getTotalValue() {
        return involvedProductList.stream()
                .map(
                        orderDetail -> orderDetail.getProductPrice()
                                .multiply(BigDecimal.valueOf(orderDetail.getProductQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public OrderDTO() {
    }

    public OrderDTO(Long orderId, String buyerEmail, Date orderDate, List<OrderDetailDTO> involvedProductList) {
        this.orderId = orderId;
        this.buyerEmail = buyerEmail;
        this.orderDate = orderDate;
        this.involvedProductList = involvedProductList;
    }

    public OrderDTO(String buyerEmail, List<OrderDetailDTO> involvedProductList) {
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

    public List<OrderDetailDTO> getInvolvedProductList() {
        return involvedProductList;
    }

    public void setInvolvedProductList(List<OrderDetailDTO> involvedProductList) {
        this.involvedProductList = involvedProductList;
    }

    public static class Validity {
        public static void checkValidity(OrderDTO order) {
            if (order == null || order.getBuyerEmail() == null || order.getInvolvedProductList() == null) {
                throw new IllegalArgumentException("Order is missing something. Check the input!");
            }
        }
    }

}
