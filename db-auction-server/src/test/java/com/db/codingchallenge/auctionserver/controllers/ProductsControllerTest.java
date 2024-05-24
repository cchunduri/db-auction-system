package com.db.codingchallenge.auctionserver.controllers;

import com.db.codingchallenge.auctionserver.dtos.ProductDto;
import com.db.codingchallenge.auctionserver.entities.Product;
import com.db.codingchallenge.auctionserver.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.db.codingchallenge.auctionserver.mappers.ProductMapper.toProductDto;
import static com.db.codingchallenge.auctionserver.testdata.MockData.mockProduct;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void testGetAllProducts() throws Exception {
        List<Product> productsList = List.of(mockProduct(), mockProduct());

        when(productService.getAllProducts()).thenReturn(productsList);

        String resultJson = objectMapper.writeValueAsString(productsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(resultJson));
    }

    @Test
    void testGetProductById() throws Exception {
        UUID productId = UUID.randomUUID();
        Product product = mockProduct(productId);
        when(productService.getProductById(productId)).thenReturn(Optional.of(product));

        String resultJson = objectMapper.writeValueAsString(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(resultJson));
    }

    @Test
    void testCreateProduct() throws Exception {
        Product product = mockProduct();
        ProductDto productDto = toProductDto(product);
        when(productService.createProduct(any())).thenReturn(product);

        String productDtoJson = objectMapper.writeValueAsString(productDto);
        String expectedResponse = objectMapper.writeValueAsString(product);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void testUpdateProduct() throws Exception {
        UUID productId = UUID.randomUUID();
        Product product = mockProduct(productId);
        ProductDto productDto = toProductDto(product);

        Product updatedProduct = Product
                        .builder()
                            .productId(productId)
                            .name("Updated Name")
                            .description("Updated Description")
                            .price(200.0)
                            .quantity(20)
                        .build();

        when(productService.updateProduct(productId, productDto)).thenReturn(Optional.of(updatedProduct));

        String productDtoJson = objectMapper.writeValueAsString(productDto);
        String expectedResponse = objectMapper.writeValueAsString(updatedProduct);

        mockMvc.perform(MockMvcRequestBuilders.put("/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void testDeleteProduct() throws Exception {
        UUID productId = UUID.randomUUID();

        doNothing().when(productService).deleteProduct(productId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}