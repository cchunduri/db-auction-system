package com.db.codingchallenge.auctionuserservice.exceptions;

public class JwtTokenExpiredException extends RuntimeException {
    public JwtTokenExpiredException(String message) {
        super(message);
    }
}
