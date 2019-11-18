package com.acme.shop.controller;

import com.acme.shop.dto.ApiError;
import com.acme.shop.dto.ProductDTO;
import com.acme.shop.dto.ResourceLocation;
import com.acme.shop.service.ProductService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/")
@Api(value = "product-api", tags = "Products API")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(path = "products", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Creates a new product", nickname = "createProduct", response = BigDecimal.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The identifier of the created product", response = BigDecimal.class),
            @ApiResponse(code = 400, message = "Invalid input", response = ApiError.class),
            @ApiResponse(code = 500, message = "An unexpected error occurred", response = ApiError.class)})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiModelProperty(value = "product_id", hidden = true)
    public ResourceLocation createProduct(@RequestBody ProductDTO product, HttpServletRequest request) {
        return new ResourceLocation(request, productService.createProduct(product));
    }

    @GetMapping(path = "products", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieves the list of all products", nickname = "getProductList", response = ProductDTO.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The list of all product", response = ProductDTO.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "An unexpected error occurred", response = ApiError.class)})
    public List<ProductDTO> getProductList() {
        return productService.getProductList();
    }

    @PutMapping(path = "products/{productId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fully updates the specified product", nickname = "updateProduct")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The specific product has been correctly updated"),
            @ApiResponse(code = 404, message = "The specific product was not found", response = ApiError.class),
            @ApiResponse(code = 500, message = "An unexpected error occurred", response = ApiError.class)})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductDTO product) {
        productService.updateProduct(productId, product);
    }

}
