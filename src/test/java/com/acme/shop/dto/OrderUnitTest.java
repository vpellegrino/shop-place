package com.acme.shop.dto;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderUnitTest {

    private static final Product A_PRODUCT = new Product(BigDecimal.ONE, "a product", new BigDecimal("1234"));
    private static final Product ANOTHER_PRODUCT = new Product(BigDecimal.ONE, "another product", new BigDecimal("450"));

    @Test
    public void givenEmptyProductList_whenConstructingOrder_thenTotalValueIsZero() {
        List<OrderDetail> orderDetailList = new ArrayList<>();

        Order order = new Order(BigDecimal.TEN, "email@email.com", orderDetailList);

        assertEquals(BigDecimal.ZERO, order.getTotalValue());
    }


    @Test
    public void givenProductListWithOneProduct_whenConstructingOrder_thenTotalValueIsProductPrice() {
        List<OrderDetail> orderDetailList = Collections.singletonList(
                new OrderDetail(A_PRODUCT, 1L, new BigDecimal("1000.12358"))
        );

        Order order = new Order(BigDecimal.TEN, "email@email.com", orderDetailList);

        assertEquals(new BigDecimal("1000.12358"), order.getTotalValue());
    }

    @Test
    public void givenProductListWithMultipleProducts_whenConstructingOrder_thenTotalValueIsProductsPricesSum() {
        List<OrderDetail> orderDetailList = Arrays.asList(
                new OrderDetail(A_PRODUCT, 1L, new BigDecimal("1000.12358")),
                new OrderDetail(ANOTHER_PRODUCT, 2L, new BigDecimal("500"))
        );

        Order order = new Order(BigDecimal.TEN, "email@email.com", orderDetailList);

        assertEquals(new BigDecimal("2000.12358"), order.getTotalValue());
    }

}
