package com.db.codingchallenge.auctionuserservice.services;

import com.db.codingchallenge.auctionuserservice.entities.AppRoles;
import com.db.codingchallenge.auctionuserservice.entities.AuctionUser;
import com.db.codingchallenge.auctionuserservice.repositories.AuctionUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class UsersServiceTest {

    @Mock
    private AuctionUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersService usersService;

    @Test
    void testCheckSellerExists() {
        // Given
        UUID existingSellerId = UUID.randomUUID();

        AuctionUser existingSeller = AuctionUser.builder()
                .userId(existingSellerId)
                .role(AppRoles.ROLE_SELLER)
                .build();

        when(
                userRepository.findByUserIdAndRole(existingSellerId, AppRoles.ROLE_SELLER)
        ).thenReturn(Optional.of(existingSeller));

        // Then
        assertTrue(usersService.checkSellerExists(existingSellerId));
    }

    @Test
    void testCheckSellerNotExists() {
        // Given
        UUID nonExistingSellerId = UUID.randomUUID();

        when(
                userRepository.findByUserIdAndRole(nonExistingSellerId, AppRoles.ROLE_SELLER)
        ).thenReturn(Optional.empty());

        // Then
        assertFalse(usersService.checkSellerExists(nonExistingSellerId));
    }

    @Test
    void testCheckBidderExists() {
        // Given
        UUID existingBidderId = UUID.randomUUID();

        AuctionUser existingBidder = AuctionUser.builder()
                .userId(existingBidderId)
                .role(AppRoles.ROLE_BIDDER)
                .build();

        when(
                userRepository.findByUserIdAndRole(existingBidderId, AppRoles.ROLE_BIDDER)
        ).thenReturn(Optional.of(existingBidder));

        // Then
        assertTrue(usersService.checkBidderExists(existingBidderId));
    }

    @Test
    void testCheckBidderNotExists() {
        // Given
        UUID nonExistingBidderId = UUID.randomUUID();

        when(
                userRepository.findByUserIdAndRole(nonExistingBidderId, AppRoles.ROLE_BIDDER)
        ).thenReturn(Optional.empty());

        // Then
        assertFalse(usersService.checkBidderExists(nonExistingBidderId));
    }
}