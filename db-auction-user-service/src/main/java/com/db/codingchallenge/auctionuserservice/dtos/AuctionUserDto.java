package com.db.codingchallenge.auctionuserservice.dtos;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AuctionUserDto(
    UUID userId,
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
) { }