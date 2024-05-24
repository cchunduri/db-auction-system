package com.db.codingchallenge.auctionserver.helpers;

import com.db.codingchallenge.auctionserver.clients.UserServiceClient;
import com.db.codingchallenge.auctionserver.dtos.BidsDto;
import com.db.codingchallenge.auctionserver.entities.Auction;
import com.db.codingchallenge.auctionserver.exceptions.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class BidsValidationHelper {

    private UserServiceClient userServiceClient;

    public void validateBid(BidsDto bidsDto, Auction auction) {

        var isBidderExists = userServiceClient.checkBidderExists(bidsDto.bidderId()).block();
        if (Boolean.FALSE.equals(isBidderExists)) {
            throw new BidderNotFound("Bidder not found");
        }

        if (!auction.getProduct().getProductId().equals(bidsDto.productId())) {
            throw new ProductNotFound("Product not assigned to auction");
        }

        if (auction.getMinPrice()>= bidsDto.bidAmount()) {
            throw new AuctionShouldHaveHigherBidAmount("Auction should have higher bid");
        }

        if (auction.getStartDate().isAfter(Instant.now())) {
            throw new BidNotPossible("Bidding not possible since auction has not started yet");
        }

        if (auction.getEndDate().isBefore(Instant.now())) {
            throw new BidNotPossible("Bidding not possible since auction has ended");
        }

        if (auction.getIsCompleted() == Boolean.TRUE) {
            throw new BidNotPossible("Bidding not possible since auction has completed");
        }
    }
}
