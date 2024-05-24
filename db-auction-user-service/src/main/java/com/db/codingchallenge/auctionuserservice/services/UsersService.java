package com.db.codingchallenge.auctionuserservice.services;

import com.db.codingchallenge.auctionuserservice.dtos.AppRolesDto;
import com.db.codingchallenge.auctionuserservice.dtos.AuctionUserDto;
import com.db.codingchallenge.auctionuserservice.entities.AppRoles;
import com.db.codingchallenge.auctionuserservice.entities.AuctionUser;
import com.db.codingchallenge.auctionuserservice.exceptions.UserAlreadyExistsException;
import com.db.codingchallenge.auctionuserservice.repositories.AuctionUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsersService {

    private AuctionUserRepository userRepository;

    public void registerUser(AuctionUserDto userRegistrationRequest) {
        var existingUserAccountByUsername = userRepository.findByUsername(userRegistrationRequest.username());
        if (existingUserAccountByUsername.isPresent()) {
            throw new UserAlreadyExistsException("Username is already exists");
        }

        var user = mapper(userRegistrationRequest);
        userRepository.save(user);
    }

    public List<AuctionUserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToAuctionUserDto)
                .toList();
    }

    private AuctionUserDto mapToAuctionUserDto(AuctionUser auctionUser) {
        return AuctionUserDto.builder()
                .userId(auctionUser.getUserId())
                .firstName(auctionUser.getFirstName())
                .lastName(auctionUser.getLastName())
                .username(auctionUser.getUsername())
                .email(auctionUser.getEmail())
                .userType(auctionUser.getRole().name())
                .createdAt(auctionUser.getCreatedAt())
                .updatedAt(auctionUser.getUpdatedAt())
                .isActive(auctionUser.isActive())
                .role(AppRolesDto.valueOf(auctionUser.getRole().name().substring(5)))
                .build();
    }

    public boolean checkSellerExists(UUID sellerId) {
        return isUserExists(sellerId, AppRoles.ROLE_SELLER).isPresent();
    }

    public boolean checkBidderExists(UUID bidderId) {
        return isUserExists(bidderId, AppRoles.ROLE_BIDDER).isPresent();
    }

    private Optional<AuctionUser> isUserExists(UUID sellerId, AppRoles role) {
        return userRepository.findByUserIdAndRole(sellerId, role);
    }

    private AuctionUser mapper(AuctionUserDto userRegistrationRequest) {
        return AuctionUser.builder()
                .username(userRegistrationRequest.username())
                .email(userRegistrationRequest.email())
                .firstName(userRegistrationRequest.firstName())
                .lastName(userRegistrationRequest.lastName())
                .isActive(true)
                .role(mapRoles(userRegistrationRequest.role()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private AppRoles mapRoles(AppRolesDto role) {
        return AppRoles.valueOf("ROLE_" + role.name());
    }
}
