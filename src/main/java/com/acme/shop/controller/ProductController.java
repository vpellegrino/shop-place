package com.acme.shop.controller;

import com.acme.shop.dto.ApiError;
import com.acme.shop.dto.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/")
@Api(value = "product-api", tags = "Products API")
public class ProductController {

    @PostMapping(path = "products", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Creates a new product", nickname = "createProduct", response = BigDecimal.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The identifier of the created product", response = BigDecimal.class),
            @ApiResponse(code = 400, message = "Invalid input", response = ApiError.class),
            @ApiResponse(code = 500, message = "An unexpected error occurred", response = ApiError.class)})
    @ResponseStatus(HttpStatus.CREATED)
    public BigDecimal createProduct(@RequestBody Product product) {
        return null;
    }

    @GetMapping(path = "products", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieves the list of all products", nickname = "getProductList", response = Product.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The list of all product", response = Product.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "An unexpected error occurred", response = ApiError.class)})
    public List<Product> getProductList() {
        return null;
    }

    @PutMapping(path = "products/{productId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fully updates the specified product", nickname = "updateProduct")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The specific product has been correctly updated"),
            @ApiResponse(code = 404, message = "The specified product was not found", response = ApiError.class),
            @ApiResponse(code = 500, message = "An unexpected error occurred", response = ApiError.class)})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable("productId") BigDecimal productId, @RequestBody Product product) {
    }

}
