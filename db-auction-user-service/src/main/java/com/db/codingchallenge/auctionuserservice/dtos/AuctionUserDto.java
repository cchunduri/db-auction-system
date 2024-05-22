package com.db.codingchallenge.auctionuserservice.dtos;

import java.time.LocalDateTime;

public record AuctionUserDto(
    String firstName,
    String lastName,
    String username,
    String password,
    String email,
    String userType,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    boolean isActive,
    AppRolesDto role
) {}