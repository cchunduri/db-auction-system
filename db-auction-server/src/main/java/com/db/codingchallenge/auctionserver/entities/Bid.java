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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@Table(name = "bid")
@AllArgsConstructor
@NoArgsConstructor
public class Bid
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "bidId", nullable = false)
    private UUID bidId;
    private Double amount;
    private UUID bidderId;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}