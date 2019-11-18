package com.acme.shop.repository;

import com.acme.shop.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProductRepository {

    Number storeProduct(Product product);

    List<Product> getProductList();

    int updateProduct(Product product);

    Map<Long, BigDecimal> getPriceForProducts(List<Long> productIdList);
}
