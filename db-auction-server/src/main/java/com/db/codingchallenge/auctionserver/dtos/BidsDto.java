package com.db.codingchallenge.auctionserver.dtos;

import java.util.UUID;
import lombok.Builder;

@Builder
public record BidsDto(
    UUID auctionId,
    Double bidAmount,
    UUID bidderId,
    UUID productId
) { }
