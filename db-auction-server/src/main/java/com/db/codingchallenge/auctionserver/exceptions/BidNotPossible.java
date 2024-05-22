package com.db.codingchallenge.auctionserver.exceptions;

public class BidNotPossible extends RuntimeException {
    public BidNotPossible(String message) {
        super(message);
    }
}
