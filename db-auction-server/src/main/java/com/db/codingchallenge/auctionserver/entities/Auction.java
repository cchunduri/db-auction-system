package com.db.codingchallenge.auctionserver.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
class Auction  {

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
    private Double currentPrice;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date endDate;

    @Column
    private UUID sellerId;

    @Column
    private UUID auctionWinnerId;

    @Column
    private Boolean isCompleted;

    @OneToMany(mappedBy = "auction")
    private List<Bid> bids;
}