package com.acme.shop.dto;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderDTOTest {

    @Test
    public void givenEmptyProductList_whenConstructingOrder_thenTotalValueIsZero() {
        List<OrderDetailDTO> orderDetailList = new ArrayList<>();

        OrderDTO order = new OrderDTO("email@email.com", orderDetailList);

        assertEquals(BigDecimal.ZERO, order.getTotalValue());
    }

    @Test
    public void givenProductListWithOneProduct_whenConstructingOrder_thenTotalValueIsProductPrice() {
        List<OrderDetailDTO> orderDetailList = Collections.singletonList(
                new OrderDetailDTO(1L, "A_PRODUCT", 1L, new BigDecimal("1000.12358"))
        );

        OrderDTO order = new OrderDTO("email@email.com", orderDetailList);

        assertEquals(new BigDecimal("1000.12358"), order.getTotalValue());
    }

    @Test
    public void givenProductListWithMultipleProducts_whenConstructingOrder_thenTotalValueIsProductsPricesSum() {
        List<OrderDetailDTO> orderDetailList = Arrays.asList(
                new OrderDetailDTO(1L, "A_PRODUCT", 1L, new BigDecimal("1000.12358")),
                new OrderDetailDTO(2L, "ANOTHER_PRODUCT", 2L, new BigDecimal("500"))
        );

        OrderDTO order = new OrderDTO("email@email.com", orderDetailList);

        assertEquals(new BigDecimal("2000.12358"), order.getTotalValue());
    }

}
