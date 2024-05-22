package com.db.codingchallenge.auctionserver.mappers;

import com.db.codingchallenge.auctionserver.dtos.BidsDto;
import com.db.codingchallenge.auctionserver.entities.Bid;

public class BidMapper {

    private BidMapper() {
        // private constructor
    }

    public static BidsDto toBidsDto(Bid bid) {
        return BidsDto.builder()
            .bidId(bid.getBidId())
            .auctionId(bid.getAuction().getAuctionId())
            .bidAmount(bid.getAmount())
            .bidderId(bid.getBidderId())
            .productId(bid.getProduct().getProductId())
            .build();
    }
}
