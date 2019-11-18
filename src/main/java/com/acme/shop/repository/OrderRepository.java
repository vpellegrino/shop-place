package com.acme.shop.repository;

import com.acme.shop.model.Order;

import java.util.Date;
import java.util.List;

public interface OrderRepository {

    Number storeOrder(Order order);

    List<Order> getOrdersInPeriod(Date startDate, Date endDate);

}
