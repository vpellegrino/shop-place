package com.acme.shop.repository;

import com.acme.shop.exception.ResourceNotFoundException;
import com.acme.shop.model.Order;
import com.acme.shop.model.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.TransactionUsageException;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.*;

@Repository
public class JDBCOrderRepository implements OrderRepository {

    private static final String CREATE_ORDER_SQL = "INSERT INTO orders (order_id, buyer_email, order_date) " +
            "VALUES(DEFAULT, :buyerEmail, :orderDate)";
    private static final String CREATE_ORDER_DETAIL_SQL = "INSERT INTO order_details (order_id, product_id, product_quantity, unit_price) " +
            "VALUES(:orderId, :productId, :productQuantity, :unitPrice)";
    private static final String GET_ORDERS_IN_PERIOD_SQL = "SELECT o.order_id as order_id, o.buyer_email as buyer_email, o.order_date as order_date, " +
            "d.product_quantity as product_quantity, d.unit_price as unit_price, " +
            "p.product_id as product_id, p.product_name as product_name " +
            "FROM orders o " +
            "JOIN order_details d ON o.order_id = d.order_id " +
            "JOIN products p on p.product_id = d.product_id " +
            "WHERE o.order_date >= :startDate AND o.order_date <= :endDate";
    private static final String ORDER_IDENTIFIER = "order_id";
    private static final String BUYER_EMAIL = "buyer_email";
    private static final String ORDER_DATE = "order_date";
    private static final String PRODUCT_ID = "product_id";
    private static final String PRODUCT_NAME = "product_name";
    private static final String PRODUCT_QUANTITY = "product_quantity";
    private static final String UNIT_PRICE = "unit_price";

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Number storeOrder(Order order) {
        Number orderId;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            orderId = insertOrder(order);

            insertOrderDetails(order, orderId);

            transactionManager.commit(txStatus);
        } catch (DataIntegrityViolationException e) {
            transactionManager.rollback(txStatus);
            throw new ResourceNotFoundException("It is not possible to insert an order without all information about involved products");
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            throw new TransactionUsageException("Transaction failed while storing the order", e);
        }

        return orderId;
    }

    private Number insertOrder(Order order) {
        Number orderId;
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(
                CREATE_ORDER_SQL,
                new BeanPropertySqlParameterSource(order), generatedKeyHolder, new String[]{ORDER_IDENTIFIER});
        orderId = generatedKeyHolder.getKey();
        return orderId;
    }

    private void insertOrderDetails(Order order, Number orderId) {
        order.getInvolvedProductList().forEach(orderDetail -> {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("orderId", orderId);
            parameters.addValue("productId", orderDetail.getProductId());
            parameters.addValue("productQuantity", orderDetail.getProductQuantity());
            parameters.addValue("unitPrice", orderDetail.getProductPrice());
            namedParameterJdbcTemplate.update(
                    CREATE_ORDER_DETAIL_SQL,
                    parameters);
        });
    }

    @Override
    public List<Order> getOrdersInPeriod(Date startDate, Date endDate) {
        Map<Long, Order> orderMap = new HashMap<>();

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("startDate", startDate);
        mapSqlParameterSource.addValue("endDate", endDate);

        namedParameterJdbcTemplate.query(
                GET_ORDERS_IN_PERIOD_SQL,
                mapSqlParameterSource,
                handleOrderMapping(orderMap)
        );

        return new ArrayList<>(orderMap.values());
    }

    private RowCallbackHandler handleOrderMapping(Map<Long, Order> orderMap) {
        return rs -> {
            Long orderId = rs.getLong(ORDER_IDENTIFIER);
            Order order;
            if (orderMap.containsKey(orderId)) {
                order = orderMap.get(orderId);
            } else {
                order = new Order(orderId, rs.getString(BUYER_EMAIL), rs.getDate(ORDER_DATE), new ArrayList<>());
                orderMap.put(orderId, order);
            }
            OrderDetail orderDetail = new OrderDetail(rs.getLong(PRODUCT_ID), rs.getString(PRODUCT_NAME), rs.getLong(PRODUCT_QUANTITY), rs.getBigDecimal(UNIT_PRICE));
            order.getInvolvedProductList().add(orderDetail);
        };
    }

}
