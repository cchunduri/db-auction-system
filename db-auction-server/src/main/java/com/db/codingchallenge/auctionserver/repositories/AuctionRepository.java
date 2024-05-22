package com.db.codingchallenge.auctionserver.repositories;

import com.db.codingchallenge.auctionserver.entities.Auction;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, UUID> { }