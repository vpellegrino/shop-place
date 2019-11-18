package com.acme.shop.repository;

import com.acme.shop.model.Product;

import java.util.List;

public interface ProductRepository {

    Number storeProduct(Product product);

    List<Product> getProductList();

    int updateProduct(Product product);

}
