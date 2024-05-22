package com.db.codingchallenge.auctionserver.exceptions;

public class BidNotFound extends RuntimeException {
    public BidNotFound(String message) {
        super(message);
    }
}
