package com.acme.shop.repository;

import com.acme.shop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JDBCProductRepository implements ProductRepository {

    private static final String CREATE_PRODUCT_SQL = "INSERT INTO products (product_id, product_name, unit_price) " +
            "VALUES(DEFAULT, :productName, :unitPrice)";
    private static final String GET_ALL_PRODUCTS_SQL = "SELECT * FROM products";
    private static final String UPDATE_PRODUCT_SQL = "UPDATE products SET product_name = :productName, unit_price = :unitPrice " +
            "WHERE product_id = :productId";
    private static final String GET_PRICE_FOR_PRODUCTS_SQL = "SELECT product_id, unit_price FROM products " +
            "WHERE product_id in (:productIdList)";
    private static final String PRODUCT_IDENTIFIER = "product_id";
    private static final String PRODUCT_NAME = "product_name";
    private static final String UNIT_PRICE = "unit_price";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Number storeProduct(Product product) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                CREATE_PRODUCT_SQL,
                new BeanPropertySqlParameterSource(product), generatedKeyHolder, new String[]{PRODUCT_IDENTIFIER});

        return generatedKeyHolder.getKey();
    }

    @Override
    public List<Product> getProductList() {
        return namedParameterJdbcTemplate.query(
                GET_ALL_PRODUCTS_SQL,
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
                UPDATE_PRODUCT_SQL,
                new BeanPropertySqlParameterSource(product));
    }

    @Override
    public Map<Long, BigDecimal> getPriceForProducts(List<Long> productIdList) {
        Map<Long, BigDecimal> productsWithPricing = new HashMap<>();

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("productIdList", productIdList);

        namedParameterJdbcTemplate.query(
                GET_PRICE_FOR_PRODUCTS_SQL,
                mapSqlParameterSource,
                rs -> {
                    productsWithPricing.put(rs.getLong(PRODUCT_IDENTIFIER), rs.getBigDecimal(UNIT_PRICE));
                }
        );

        return productsWithPricing;
    }

}
