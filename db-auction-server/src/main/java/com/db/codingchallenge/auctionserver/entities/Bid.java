package com.db.codingchallenge.auctionserver.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bid")
public class Bid
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "bidId", nullable = false)
    private UUID bidId;

    @Column(name = "bidAmount", nullable = false)
    private Double bidAmount;

    @Column(name = "bidderId", nullable = false)
    private UUID bidderId;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

}