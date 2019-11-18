package com.acme.shop.repository;

import com.acme.shop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JDBCProductRepository implements ProductRepository {

    private static final String PRODUCT_IDENTIFIER = "product_id";
    private static final String PRODUCT_NAME = "product_name";
    private static final String UNIT_PRICE = "unit_price";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Number storeProduct(Product product) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                "INSERT INTO products (product_id, product_name, unit_price) " +
                        "VALUES(DEFAULT, :productName, :unitPrice)",
                new BeanPropertySqlParameterSource(product), generatedKeyHolder, new String[]{PRODUCT_IDENTIFIER});

        return generatedKeyHolder.getKey();
    }

    @Override
    public List<Product> getProductList() {
        return namedParameterJdbcTemplate.query(
                "select * from products",
                (rs, rowNum) ->
                        new Product(
                                rs.getLong(PRODUCT_IDENTIFIER),
                                rs.getString(PRODUCT_NAME),
                                rs.getBigDecimal(UNIT_PRICE)
                        )
        );
    }

    @Override
    public int updateProduct(Product product) {
        return namedParameterJdbcTemplate.update(
                "update products set product_name = :productName, unit_price = :unitPrice " +
                        "where product_id = :productId",
                new BeanPropertySqlParameterSource(product));
    }

}
