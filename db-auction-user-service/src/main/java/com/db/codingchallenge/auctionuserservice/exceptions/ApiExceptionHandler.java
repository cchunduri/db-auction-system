package com.db.codingchallenge.auctionuserservice.exceptions;

import com.db.codingchallenge.auctionuserservice.dtos.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentialsException(
        BadCredentialsException ex, WebRequest request
    ) {
        return handleExceptionInternal(
            ex,
            new ApiError("You entered wrong credentials"),
            new HttpHeaders(),
            HttpStatus.CONFLICT,
            request
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    protected ResponseEntity<Object> handleUserAlreadyExists(
        UserAlreadyExistsException exception, WebRequest request
    ) {
        return handleExceptionInternal(
            exception,
            new ApiError(exception.getMessage() != null ? exception.getMessage() : "User already exists"),
            new HttpHeaders(),
            HttpStatus.CONFLICT,
            request
        );
    }

    @ExceptionHandler(JwtTokenExpiredException.class)
    protected ResponseEntity<Object> handleJwtTokenExpiry(
        JwtTokenExpiredException exception, WebRequest request
    ) {
        return handleExceptionInternal(
            exception,
            new ApiError("JWT token expired please login again"),
            new HttpHeaders(),
            HttpStatus.CONFLICT,
            request
        );
    }

}
