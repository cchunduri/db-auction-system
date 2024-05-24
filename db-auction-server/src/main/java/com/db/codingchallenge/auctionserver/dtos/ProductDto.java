package com.db.codingchallenge.auctionserver.dtos;

import lombok.Builder;

@Builder
public record ProductDto(
    String name,
    String description,
    Double price,
    int quantity
) { }
