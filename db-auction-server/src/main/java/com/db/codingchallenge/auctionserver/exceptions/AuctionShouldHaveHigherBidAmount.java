package com.db.codingchallenge.auctionserver.exceptions;

public class AuctionShouldHaveHigherBidAmount extends RuntimeException {

    public AuctionShouldHaveHigherBidAmount(String message) {
        super(message);
    }
}
