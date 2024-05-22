package com.db.codingchallenge.auctionserver.repositories;

import com.db.codingchallenge.auctionserver.entities.Bid;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, UUID> {  }