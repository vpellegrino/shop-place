package com.acme.shop.service;

import com.acme.shop.dto.OrderDTO;
import com.acme.shop.model.Order;
import com.acme.shop.repository.OrderRepository;
import com.acme.shop.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-mm-dd", Locale.ENGLISH);

    private OrderDTO anOrder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        anOrder = new OrderDTO("buyer@email.com", new ArrayList<>());
    }

    @Test
    public void whenCreatingAnOrder_thenReturnedIdIsTheOneRetrievedFromRepository() {
        when(productRepository.getPriceForProducts(any())).thenReturn(new HashMap<>());
        when(orderRepository.storeOrder(any(Order.class))).thenReturn(1L);

        assertEquals(1L, (long) orderService.createOrder(anOrder));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingEmptyOrder_thenException() {
        orderService.createOrder(new OrderDTO());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenStartDateAfterEndDate_whenGettingOrders_thenException() throws ParseException {
        orderService.getOrdersInPeriod(dateFormat.parse("2018-05-05"), dateFormat.parse("1999-05-05"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenStartDateNull_whenGettingOrders_thenException() throws ParseException {
        orderService.getOrdersInPeriod(null, dateFormat.parse("1999-05-05"));
    }

}
