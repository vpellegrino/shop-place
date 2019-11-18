package com.acme.shop.service;

import com.acme.shop.dto.ProductDTO;
import com.acme.shop.model.Product;
import com.acme.shop.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private ProductDTO aProduct;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        aProduct = new ProductDTO(1L, "a product", new BigDecimal("1234"));
    }

    @Test
    public void whenCreatingProduct_thenReturnedIdIsTheOneRetrievedFromRepository() {
        when(productRepository.storeProduct(any(Product.class))).thenReturn(1);

        assertEquals(1, productService.createProduct(aProduct));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingProductWithEmptyProperties_thenException() {
        productService.createProduct(new ProductDTO());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenUpdatingProductWithEmptyProperties_thenException() {
        productService.updateProduct(1L, new ProductDTO());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenUpdatingProductWithoutSpecifyingId_thenException() {
        productService.updateProduct(null, new ProductDTO());
    }

}
