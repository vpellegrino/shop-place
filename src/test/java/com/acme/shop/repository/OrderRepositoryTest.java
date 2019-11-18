package com.acme.shop.repository;

import com.acme.shop.config.TestConfiguration;
import com.acme.shop.exception.ResourceNotFoundException;
import com.acme.shop.model.Order;
import com.acme.shop.model.OrderDetail;
import com.acme.shop.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-mm-dd", Locale.ENGLISH);

    private Order anOrder;

    @Before
    public void setUp() {
        Product aProduct = new Product("a product", new BigDecimal("1234.5678"));
        Number aProductId = productRepository.storeProduct(aProduct);
        aProduct.setProductId(aProductId.longValue());

        Product anotherProduct = new Product("another product", new BigDecimal("45"));
        Number anotherProductId = productRepository.storeProduct(anotherProduct);
        anotherProduct.setProductId(anotherProductId.longValue());

        OrderDetail anOrderDetail = new OrderDetail(aProduct.getProductId(), aProduct.getProductName(), 1L, aProduct.getUnitPrice());
        OrderDetail anotherOrderDetail = new OrderDetail(anotherProduct.getProductId(), anotherProduct.getProductName(), 2L, anotherProduct.getUnitPrice());
        anOrder = new Order("buyer@acme.com", Arrays.asList(anOrderDetail, anotherOrderDetail));
    }

    @Test
    public void whenStoringAnOrder_thenReturnedOrderIdIsNotNull() {
        assertNotNull(orderRepository.storeOrder(anOrder));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void givenAnOrderContainingNotStoredProducts_whenStoringIt_thenException() {
        anOrder.getInvolvedProductList()
                .forEach(orderDetail -> orderDetail.setProductId(null));
        orderRepository.storeOrder(anOrder);
    }

    @Test
    public void givenDatesAroundOrderCreationDate_whenRetrievingOrdersInThatPeriod_thenOrderIsReturned() {
        orderRepository.storeOrder(anOrder);

        Date now = new Date();
        LocalDateTime localDateTime = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Date yesterday = Date.from(localDateTime.minusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        Date tomorrow = Date.from(localDateTime.plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        List<Order> ordersInPeriod = orderRepository.getOrdersInPeriod(yesterday, tomorrow);

        assertFalse(ordersInPeriod.isEmpty());
        assertEquals(1, ordersInPeriod.size());
        Order retrievedOrder = ordersInPeriod.get(0);
        assertNotNull(retrievedOrder);
        assertEquals(anOrder.getBuyerEmail(), retrievedOrder.getBuyerEmail());
        assertNotNull(retrievedOrder.getInvolvedProductList());
        assertEquals(2, retrievedOrder.getInvolvedProductList().size());
    }

    @Test
    public void givenDatesWhenNoOrderExists_whenRetrievingOrdersInThatPeriod_thenEmptyListIsReturned() throws ParseException {
        assertThat("Order list should be empty for such period",
                orderRepository.getOrdersInPeriod(dateFormat.parse("2018-05-05"), dateFormat.parse("2018-05-06")).isEmpty());
    }

}