package com.db.codingchallenge.auctionuserservice.services;

import com.db.codingchallenge.auctionuserservice.dtos.AppRolesDto;
import com.db.codingchallenge.auctionuserservice.dtos.AuctionUserDto;
import com.db.codingchallenge.auctionuserservice.entities.AppRoles;
import com.db.codingchallenge.auctionuserservice.entities.AuctionUser;
import com.db.codingchallenge.auctionuserservice.exceptions.UserAlreadyExistsException;
import com.db.codingchallenge.auctionuserservice.repositories.AuctionUserRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsersService {

    private AuctionUserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    public void registerUser(AuctionUserDto userRegistrationRequest) {
        var existingUserAccountByUsername = userRepository.findByUsername(userRegistrationRequest.username());
        if (existingUserAccountByUsername.isPresent()) {
            throw new UserAlreadyExistsException("Username is already exists");
        }

        var user = mapper(userRegistrationRequest, passwordEncoder);
        userRepository.save(user);
    }


    private AuctionUser mapper(AuctionUserDto userRegistrationRequest, PasswordEncoder passwordEncoder) {
        return AuctionUser.builder()
            .username(userRegistrationRequest.username())
            .password(passwordEncoder.encode(userRegistrationRequest.password()))
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


    public List<AuctionUserDto> getAllUsers() {
        return userRepository.findAll().stream()
            .map(this::mapToAuctionUserDto)
            .toList();
    }


    private AuctionUserDto mapToAuctionUserDto(AuctionUser auctionUser) {
        return new AuctionUserDto(
            auctionUser.getFirstName(),
            auctionUser.getLastName(),
            auctionUser.getUsername(),
            auctionUser.getPassword(),
            auctionUser.getEmail(),
            auctionUser.getRole().name(),
            auctionUser.getCreatedAt(),
            auctionUser.getUpdatedAt(),
            auctionUser.isActive(),
            AppRolesDto.valueOf(auctionUser.getRole().name().substring(5))
        );
    }
}
