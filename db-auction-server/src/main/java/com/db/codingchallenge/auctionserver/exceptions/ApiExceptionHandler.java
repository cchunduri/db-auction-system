package com.db.codingchallenge.auctionserver.exceptions;

import com.db.codingchallenge.auctionserver.entities.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuctionNotFound.class)
    protected ResponseEntity<Object> handleAuctionNotFound(
        AuctionNotFound ex, WebRequest request
    ) {
        return handleExceptionInternal(
            ex,
            new ApiError("Auction not found"),
            new HttpHeaders(),
            HttpStatus.NOT_FOUND,
            request
        );
    }

    @ExceptionHandler(ProductNotFound.class)
    protected ResponseEntity<Object> handleProductNotFound(
        ProductNotFound ex, WebRequest request
    ) {
        return handleExceptionInternal(
            ex,
            new ApiError("Product not found"),
            new HttpHeaders(),
            HttpStatus.NOT_FOUND,
            request
        );
    }

    @ExceptionHandler(BidNotFound.class)
    protected ResponseEntity<Object> handleBidNotFound(
        BidNotFound ex, WebRequest request
    ) {
        return handleExceptionInternal(
            ex,
            new ApiError("Bid not found"),
            new HttpHeaders(),
            HttpStatus.NOT_FOUND,
            request
        );
    }

    @ExceptionHandler(AuctionShouldHaveHigherBidAmount.class)
    protected ResponseEntity<Object> handleAuctionShouldHaveHigherBidAmount(
        AuctionShouldHaveHigherBidAmount ex, WebRequest request
    ) {
        return handleExceptionInternal(
            ex,
            new ApiError("Minimum bid amount should be higher than the auction minimum price"),
            new HttpHeaders(),
            HttpStatus.CONFLICT,
            request
        );
    }

    @ExceptionHandler(BidNotPossible.class)
    protected ResponseEntity<Object> handleBidNotPossible(
        BidNotPossible ex, WebRequest request
    ) {
        return handleExceptionInternal(
            ex,
            new ApiError(ex.getMessage()),
            new HttpHeaders(),
            HttpStatus.CONFLICT,
            request
        );
    }

    @ExceptionHandler(BidderNotFound.class)
    protected ResponseEntity<Object> handleBidderNotFound(
        BidderNotFound ex, WebRequest request
    ) {
        return handleExceptionInternal(
            ex,
            new ApiError(ex.getMessage()),
            new HttpHeaders(),
            HttpStatus.NOT_FOUND,
            request
        );
    }

    @ExceptionHandler(SellerNotFound.class)
    protected ResponseEntity<Object> handleSellerNotFound(
        SellerNotFound ex, WebRequest request
    ) {
        return handleExceptionInternal(
            ex, new ApiError(ex.getMessage()), new HttpHeaders(), HttpStatus.NOT_FOUND, request
        );
    }
}
