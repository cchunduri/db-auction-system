package com.db.codingchallenge.auctionserver.dtos;

public record ProductDto(
    String name,
    String description,
    Double price,
    int quantity
) { }
