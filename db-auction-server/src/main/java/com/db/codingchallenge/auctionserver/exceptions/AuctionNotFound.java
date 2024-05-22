package com.db.codingchallenge.auctionserver.exceptions;

public class AuctionNotFound extends RuntimeException {

    public AuctionNotFound(String message) {
        super(message);
    }
}
