package com.db.codingchallenge.auctionuserservice.repositories;

import com.db.codingchallenge.auctionuserservice.entities.AuctionUser;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionUserRepository extends JpaRepository<AuctionUser, UUID> {
    Optional<AuctionUser> findByUsername(String username);
}