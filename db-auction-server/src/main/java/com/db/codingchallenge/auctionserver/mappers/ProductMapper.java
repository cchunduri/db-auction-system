package com.db.codingchallenge.auctionserver.mappers;

import com.db.codingchallenge.auctionserver.dtos.ProductDto;
import com.db.codingchallenge.auctionserver.entities.Product;

public class ProductMapper {

    private ProductMapper() {
        // private constructor
    }

    public static Product toProductEntity(ProductDto productDto) {
        return Product.builder()
                .name(productDto.name())
                .description(productDto.description())
                .price(productDto.price())
                .quantity(productDto.quantity())
                .build();
    }

    public static ProductDto toProductDto(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }
}
