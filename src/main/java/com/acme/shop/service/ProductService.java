package com.acme.shop.service;

import com.acme.shop.dto.ProductDTO;
import com.acme.shop.exception.ResourceNotFoundException;
import com.acme.shop.model.Product;
import com.acme.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.acme.shop.dto.ProductDTO.Validity.checkValidity;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Long createProduct(ProductDTO product) {
        checkValidity(product);

        Product productToStore = new Product();
        productToStore.setProductName(product.getProductName());
        productToStore.setUnitPrice(product.getUnitPrice());

        Number generatedId = productRepository.storeProduct(productToStore);
        return Objects.requireNonNull(generatedId, "There was an issue while generating an id for the product").longValue();
    }

    public void updateProduct(Long productId, ProductDTO product) {
        if (productId == null) {
            throw new IllegalArgumentException("In order to perform an update, product identifier should be specified");
        }
        checkValidity(product);

        Product productToUpdate = new Product();
        productToUpdate.setProductId(productId);
        productToUpdate.setProductName(product.getProductName());
        productToUpdate.setUnitPrice(product.getUnitPrice());

        int updatedRows = productRepository.updateProduct(productToUpdate);
        if (updatedRows == 0) {
            throw new ResourceNotFoundException("Update failed - The given product identifier does not correspond to any stored product");
        }
    }

    public List<ProductDTO> getProductList() {
        List<Product> productList = productRepository.getProductList();
        return productList.stream()
                .map(product -> new ProductDTO(product.getProductId(), product.getProductName(), product.getUnitPrice()))
                .collect(Collectors.toList());
    }

}
