package com.db.codingchallenge.auctionuserservice.repositories;

import com.db.codingchallenge.auctionuserservice.entities.AppRoles;
import com.db.codingchallenge.auctionuserservice.entities.AuctionUser;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionUserRepository extends JpaRepository<AuctionUser, UUID> {
    Optional<AuctionUser> findByUsername(String username);
    Optional<AuctionUser> findByUserIdAndRole(UUID userId, AppRoles role);
}