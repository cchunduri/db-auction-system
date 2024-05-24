package com.db.codingchallenge.auctionserver.testdata;

import com.db.codingchallenge.auctionserver.dtos.AuctionDto;
import com.db.codingchallenge.auctionserver.dtos.BidsDto;
import com.db.codingchallenge.auctionserver.entities.Auction;
import com.db.codingchallenge.auctionserver.entities.Bid;
import com.db.codingchallenge.auctionserver.entities.Product;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

public class MockData {

    public static Bid mockBidEntity(Auction auction, Product product, Double amount) {
        return Bid.builder()
                .bidId(UUID.randomUUID())
                .bidderId(UUID.randomUUID())
                .amount(amount)
                .auction(auction)
                .product(product)
                .build();
    }

    public static Bid mockBidEntity(UUID bidId, Auction auction, Product product, Double amount) {
        return Bid.builder()
                .bidId(bidId)
                .bidderId(UUID.randomUUID())
                .amount(amount)
                .auction(auction)
                .product(product)
                .build();
    }

    public static Bid mockBidEntity(Auction auction, Product product) {
        return Bid.builder()
                .bidId(UUID.randomUUID())
                .bidderId(UUID.randomUUID())
                .amount(100.0)
                .auction(auction)
                .product(product)
                .build();
    }

    public static Product mockProduct() {
        return Product.builder()
                .productId(UUID.randomUUID())
                .name("Test Product")
                .description("This is a test product")
                .quantity(10)
                .build();
    }

    public static Product mockProduct(UUID productId) {
        return Product.builder()
                .productId(productId)
                .name("Test Product")
                .description("This is a test product")
                .quantity(10)
                .build();
    }

    public static Auction mockAuction() {
        return Auction.builder()
                .auctionId(UUID.randomUUID())
                .name("Test Auction")
                .description("This is a test auction")
                .minPrice(100.0)
                .startDate(Instant.now())
                .endDate(Instant.now().plusSeconds(86400)) // plus 1 day
                .sellerId(UUID.randomUUID())
                .auctionWinnerId(UUID.randomUUID())
                .isCompleted(false)
                .product(mockProduct())
                .bids(null)
                .build();
    }

    public static Auction mockAuction(Product product) {
        return Auction.builder()
                .auctionId(UUID.randomUUID())
                .name("Test Auction")
                .description("This is a test auction")
                .minPrice(100.0)
                .startDate(Instant.now())
                .endDate(Instant.now().plusSeconds(86400)) // plus 1 day
                .sellerId(UUID.randomUUID())
                .auctionWinnerId(UUID.randomUUID())
                .isCompleted(false)
                .product(product)
                .bids(null)
                .build();
    }

    public static AuctionDto mockAuctionDto() {
        var auctionId = UUID.randomUUID();
        return AuctionDto.builder()
                .auctionId(auctionId)
                .name("Test Auction")
                .description("This is a test auction")
                .productId(UUID.randomUUID())
                .minPrice(100.0)
                .winningPrice(200.0)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .sellerId(UUID.randomUUID())
                .auctionWinnerId(UUID.randomUUID())
                .isCompleted(false)
                .bids(Collections.singletonList(mockBidDto(auctionId)))
                .build();
    }

    public static BidsDto mockBidDto(UUID auctionId) {
        return BidsDto.builder()
                .bidId(UUID.randomUUID())
                .auctionId(auctionId)
                .bidderId(UUID.randomUUID())
                .productId(UUID.randomUUID())
                .bidAmount(100.0)
                .build();
    }

    public static BidsDto mockBidDto(UUID auctionId, UUID productId) {
        return BidsDto.builder()
                .bidId(UUID.randomUUID())
                .auctionId(auctionId)
                .bidderId(UUID.randomUUID())
                .productId(productId)
                .bidAmount(100.0)
                .build();
    }

    public static BidsDto mockBidDto(UUID auctionId, UUID productId, double amount) {
        return BidsDto.builder()
                .auctionId(auctionId)
                .productId(productId)
                .bidderId(UUID.randomUUID())
                .bidAmount(amount)
                .build();
    }
}
