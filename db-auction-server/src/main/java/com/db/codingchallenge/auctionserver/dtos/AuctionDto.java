package com.db.codingchallenge.auctionserver.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuctionDto(
    UUID auctionId,
    String name,
    String description,
    UUID productId,
    Double minPrice,
    Double winningPrice,
    LocalDateTime startDate,
    LocalDateTime endDate,
    UUID sellerId,
    UUID auctionWinnerId,
    Boolean isCompleted,
    List<BidsDto> bids
) { }
