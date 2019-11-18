package com.acme.shop.repository;

import com.acme.shop.config.TestConfigurationContext;
import com.acme.shop.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfigurationContext.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product A_PRODUCT;

    @Before
    public void setUp() {
        A_PRODUCT = new Product("a product", new BigDecimal("1234.5678"));
    }

    @Test
    public void whenStoringProduct_thenReturnedProductIdIsNotNull() {
        Number productId = productRepository.storeProduct(A_PRODUCT);

        assertNotNull(productId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenStoringEmptyProduct_thenException() {
        productRepository.storeProduct(null);
    }

    @Test
    public void givenProduct_whenStoringItMultipleTimes_thenReturnedIdsAreDifferent() {
        Number firstId = productRepository.storeProduct(A_PRODUCT);
        Number secondId = productRepository.storeProduct(A_PRODUCT);

        assertNotEquals(firstId, secondId);
    }

    @Test
    public void whenNoProductsAreStored_productListIsEmpty() {
        assertTrue("Product list should be empty when no products are stored", productRepository.getProductList().isEmpty());
    }

    @Test
    public void whenSingleProductIsStored_productListContainsOnlyIt() {
        productRepository.storeProduct(A_PRODUCT);
        assertFalse("Product list should not be empty", productRepository.getProductList().isEmpty());
        assertEquals(1, productRepository.getProductList().size());
    }

    @Test
    public void givenProduct_whenUpdating_thenCorrectlyUpdated() {
        Number productId = productRepository.storeProduct(A_PRODUCT);
        assertNotNull(productId);

        int updatedRows = productRepository.updateProduct(new Product(productId.longValue(), "another name", BigDecimal.ZERO));
        assertEquals(1, updatedRows);

        Product updatedProduct = retrieveUpdatedProductFromTheList(productId);
        assertNotNull(updatedProduct);
        assertEquals("product id should be the same, after update", productId.longValue(), (long) updatedProduct.getProductId());
        assertEquals("another name", updatedProduct.getProductName());
        assertEquals(BigDecimal.ZERO, updatedProduct.getUnitPrice());
    }

    @Test
    public void whenUpdatingNonExistingProduct_thenNoRowsUpdated() {
        assertEquals(0, productRepository.updateProduct(new Product(-999L, "a product name", BigDecimal.ZERO)));
    }

    private Product retrieveUpdatedProductFromTheList(Number productId) {
        return productRepository.getProductList()
                .stream()
                .filter(product -> product.getProductId() == productId.longValue())
                .findAny().orElse(null);
    }

}
