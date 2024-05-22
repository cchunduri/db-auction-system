package com.db.codingchallenge.auctionserver.exceptions;

public class SellerNotFound extends RuntimeException {
    public SellerNotFound(String message) {
        super(message);
    }
}
