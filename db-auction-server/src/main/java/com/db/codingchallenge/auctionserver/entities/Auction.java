package com.db.codingchallenge.auctionserver.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auction  {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID auctionId;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private Double minPrice;

    @Column
    private Double winningPrice;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Instant startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Instant endDate;

    @Column
    private UUID sellerId;

    @Column
    private UUID auctionWinnerId;

    @Column
    private Boolean isCompleted;

    @OneToMany(mappedBy = "auction")
    private List<Bid> bids;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
}