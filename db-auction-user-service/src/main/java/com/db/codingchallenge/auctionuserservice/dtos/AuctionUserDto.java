package com.db.codingchallenge.auctionuserservice.dtos;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record AuctionUserDto(
    UUID userId,
    String firstName,
    String lastName,
    String username,
    String email,
    String userType,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    boolean isActive,
    AppRolesDto role
) { }