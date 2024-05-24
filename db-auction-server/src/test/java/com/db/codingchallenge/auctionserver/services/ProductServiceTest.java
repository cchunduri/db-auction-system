package com.db.codingchallenge.auctionserver.services;

import com.db.codingchallenge.auctionserver.entities.Product;
import com.db.codingchallenge.auctionserver.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.db.codingchallenge.auctionserver.mappers.ProductMapper.toProductDto;
import static com.db.codingchallenge.auctionserver.testdata.MockData.mockProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testGetAllProducts() {
        var productList = List.of(mockProduct(), mockProduct());

        when(productRepository.findAll()).thenReturn(productList);

        var result = productService.getAllProducts();

        assertEquals(2, result.size());
        assertThat(result)
                .extracting(Product::getProductId)
                .containsAnyElementsOf(
                        productList.stream().map(Product::getProductId).toList()
                );
    }

    @Test
    void testGetProductById() {
        var mockProduct = mockProduct();

        when(productRepository.findById(mockProduct.getProductId()))
                .thenReturn(Optional.of(mockProduct));

        var result = productService.getProductById(mockProduct.getProductId());

        assertTrue(result.isPresent());
        assertEquals(mockProduct.getProductId(), result.get().getProductId());
    }

    @Test
    void testCreateProduct() {
        var mockProduct = mockProduct();
        var mockProductDto = toProductDto(mockProduct);

        when(productRepository.save(any(Product.class)))
                .thenReturn(mockProduct);

        var result = productService.createProduct(mockProductDto);

        assertEquals(mockProduct, result);
    }

    @Test
    void testUpdateProduct() {
        var mockProduct = mockProduct();
        var mockProductDto = toProductDto(mockProduct);

        var updatedProduct = Product.builder()
                    .name("Updated Name")
                    .description("Updated Description")
                    .price(200.0)
                    .quantity(20)
                .build();

        when(productRepository.findById(mockProduct.getProductId()))
                .thenReturn(Optional.of(mockProduct));
        when(productRepository.save(any(Product.class)))
                .thenReturn(updatedProduct);

        var result = productService.updateProduct(mockProduct.getProductId(), mockProductDto);

        assertTrue(result.isPresent());
        assertEquals(updatedProduct, result.get());
    }

    @Test
    void testDeleteProduct() {
        var mockProduct = mockProduct();

        productService.deleteProduct(mockProduct.getProductId());

        verify(productRepository, times(1))
                .deleteById(mockProduct.getProductId());
    }
}