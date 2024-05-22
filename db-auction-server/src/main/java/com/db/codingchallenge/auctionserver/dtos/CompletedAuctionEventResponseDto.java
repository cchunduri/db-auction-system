package com.db.codingchallenge.auctionserver.dtos;

import java.util.UUID;
import lombok.Builder;

@Builder
public record CompletedAuctionEventResponseDto(
    UUID auctionId,
    BidsDto winningBid
) {  }
