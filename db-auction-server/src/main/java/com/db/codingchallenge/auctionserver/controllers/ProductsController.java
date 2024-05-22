package com.db.codingchallenge.auctionserver.controllers;

import com.db.codingchallenge.auctionserver.dtos.ProductDto;
import com.db.codingchallenge.auctionserver.entities.Product;
import com.db.codingchallenge.auctionserver.services.ProductService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductsController {

    private final ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(
        @PathVariable UUID productId
    ) {
        return productService.getProductById(productId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product createProduct(
        @RequestBody ProductDto productDto
    ) {
        return productService.createProduct(productDto);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(
        @PathVariable UUID productId,
        @RequestBody ProductDto productDto
    ) {
        return productService.updateProduct(productId, productDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }
}
