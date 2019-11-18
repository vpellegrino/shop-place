package com.acme.shop.service;

import com.acme.shop.dto.OrderDTO;
import com.acme.shop.dto.OrderDetailDTO;
import com.acme.shop.model.Order;
import com.acme.shop.model.OrderDetail;
import com.acme.shop.repository.OrderRepository;
import com.acme.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.acme.shop.dto.OrderDTO.Validity.checkValidity;
import static com.acme.shop.dto.OrderDetailDTO.Validity.checkDetailValidity;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public Long createOrder(OrderDTO order) {
        checkValidity(order);

        List<Long> productIdsInvolvedInOrder = order.getInvolvedProductList().stream().map(OrderDetailDTO::getProductId).collect(Collectors.toList());
        Map<Long, BigDecimal> productsPricing = productRepository.getPriceForProducts(productIdsInvolvedInOrder);

        Order orderToStore = new Order();
        orderToStore.setBuyerEmail(order.getBuyerEmail());
        orderToStore.setOrderDate(new Date());
        orderToStore.setInvolvedProductList(
                order.getInvolvedProductList()
                        .stream()
                        .map(orderDetailMappingFn(productsPricing))
                        .collect(Collectors.toList())
        );

        return Objects.requireNonNull(orderRepository.storeOrder(orderToStore), "There was an issue while generating an id for the order").longValue();
    }

    private Function<OrderDetailDTO, OrderDetail> orderDetailMappingFn(Map<Long, BigDecimal> productsPricing) {
        return detailDTO -> {
            checkDetailValidity(detailDTO);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProductPrice(productsPricing.get(detailDTO.getProductId()));
            orderDetail.setProductQuantity(detailDTO.getProductQuantity());
            orderDetail.setProductId(detailDTO.getProductId());
            return orderDetail;
        };
    }

    public List<OrderDTO> getOrdersInPeriod(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Retrieving order - Dates cannot be null");
        }

        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Retrieving order - Start date cannot be after end date");
        }

        return orderRepository.getOrdersInPeriod(startDate, endDate)
                .stream()
                .map(order -> {
                    List<OrderDetailDTO> involvedProductList = order.getInvolvedProductList()
                            .stream()
                            .map(detail -> new OrderDetailDTO(detail.getProductId(), detail.getProductName(), detail.getProductQuantity(), detail.getProductPrice()))
                            .collect(Collectors.toList());
                    return new OrderDTO(order.getOrderId(), order.getBuyerEmail(), order.getOrderDate(), involvedProductList);
                })
                .collect(Collectors.toList());
    }

}
