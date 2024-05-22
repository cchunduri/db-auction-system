package com.db.codingchallenge.auctionserver.exceptions;

public class BidderNotFound  extends RuntimeException {
    public BidderNotFound(String message) {
        super(message);
    }
}
