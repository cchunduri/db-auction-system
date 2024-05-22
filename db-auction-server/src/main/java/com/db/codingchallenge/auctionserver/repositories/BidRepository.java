package com.db.codingchallenge.auctionserver.repositories;

import com.db.codingchallenge.auctionserver.entities.Bid;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid, UUID> {  }