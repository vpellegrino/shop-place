package com.acme.shop.config;

import com.acme.shop.repository.JDBCOrderRepository;
import com.acme.shop.repository.JDBCProductRepository;
import com.acme.shop.repository.OrderRepository;
import com.acme.shop.repository.ProductRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Configuration class, used only for testing purpose
 * It needs to expose the H2 (in memory) data source bean,
 * As well as the repository beans used in the test classes
 */
@Configuration
@EnableAutoConfiguration
public class TestConfiguration {

    @Bean
    public DataSource h2DataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db.sql")
                .build();
    }

    @Bean
    public ProductRepository productRepository() {
        return new JDBCProductRepository();
    }

    @Bean
    public OrderRepository orderRepository() {
        return new JDBCOrderRepository();
    }
}
