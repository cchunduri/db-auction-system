package com.db.codingchallenge.auctionserver.mappers;

import com.db.codingchallenge.auctionserver.dtos.AuctionDto;
import com.db.codingchallenge.auctionserver.entities.Auction;
import com.db.codingchallenge.auctionserver.entities.Product;

import java.time.ZoneId;

public class AuctionMapper {

    private AuctionMapper() {
        // private constructor
    }

    public static AuctionDto toDto(Auction auction) {
        return AuctionDto.builder()
                .auctionId(auction.getAuctionId())
                .name(auction.getName())
                .description(auction.getDescription())
                .productId(auction.getProduct().getProductId())
                .minPrice(auction.getMinPrice())
                .winningPrice(auction.getWinningPrice())
                .startDate(auction.getStartDate().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .endDate(auction.getEndDate().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .sellerId(auction.getSellerId())
                .auctionWinnerId(auction.getAuctionWinnerId())
                .isCompleted(auction.getIsCompleted())
                .build();
    }

    public static Auction toEntity(
            AuctionDto auctionDto,
            Product product
    ) {
        return Auction.builder()
                .auctionId(auctionDto.auctionId())
                .name(auctionDto.name())
                .description(auctionDto.description())
                .product(product)
                .minPrice(auctionDto.minPrice())
                .winningPrice(auctionDto.winningPrice())
                .startDate(auctionDto.startDate().atZone(ZoneId.systemDefault()).toInstant())
                .endDate(auctionDto.endDate().atZone(ZoneId.systemDefault()).toInstant())
                .sellerId(auctionDto.sellerId())
                .auctionWinnerId(auctionDto.auctionWinnerId())
                .isCompleted(auctionDto.isCompleted())
                .build();
    }
}
