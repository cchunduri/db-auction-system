package com.db.codingchallenge.auctionserver.services;

import com.db.codingchallenge.auctionserver.clients.UserServiceClient;
import com.db.codingchallenge.auctionserver.dtos.BidsDto;
import com.db.codingchallenge.auctionserver.entities.Auction;
import com.db.codingchallenge.auctionserver.entities.Bid;
import com.db.codingchallenge.auctionserver.exceptions.AuctionNotFound;
import com.db.codingchallenge.auctionserver.exceptions.AuctionShouldHaveHigherBidAmount;
import com.db.codingchallenge.auctionserver.exceptions.BidNotFound;
import com.db.codingchallenge.auctionserver.exceptions.BidNotPossible;
import com.db.codingchallenge.auctionserver.mappers.BidMapper;
import com.db.codingchallenge.auctionserver.repositories.BidRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.db.codingchallenge.auctionserver.mappers.BidMapper.toBidsDto;

@Service
@AllArgsConstructor
public class BidService {

    private BidRepository bidRepository;
    private AuctionService auctionService;
    private UserServiceClient userServiceClient;

    public List<BidsDto> getAllBids() {
        return bidRepository.findAll().stream().map(BidMapper::toBidsDto).toList();
    }

    public Optional<Bid> getBidById(UUID id) {
        return bidRepository.findById(id);
    }

    public BidsDto createBid(BidsDto bidsDto) {

        Auction auction = auctionService.getAuction(bidsDto.auctionId())
            .orElseThrow(() -> new AuctionNotFound("Auction not found"));

        if (!auction.getProduct().getProductId().equals(bidsDto.productId())) {
            throw new BidNotFound("Product not found");
        }

        if (auction.getMinPrice()>= bidsDto.bidAmount()) {
            throw new AuctionShouldHaveHigherBidAmount("Auction should have higher bid");
        }

        if (auction.getStartDate().isAfter(Instant.now())) {
            throw new BidNotPossible("Bidding not possible since auction has not started yet");
        }

        if (auction.getEndDate().isBefore(Instant.now())) {
            throw new BidNotPossible("Bidding not possible since auction has ended");
        }

        var isBidderExists = userServiceClient.checkBidderExists(bidsDto.bidderId()).block();
        if (Boolean.FALSE.equals(isBidderExists)) {
            throw new BidNotFound("Bidder not found");
        }

        var bid = toEntity(bidsDto, auction);
        return toBidsDto(bidRepository.save(bid));
    }

    public BidsDto updateBid(UUID bidId, BidsDto bidsDto) {
        return bidRepository.findById(bidId)
            .map(bid -> {
                bid.setAmount(bidsDto.bidAmount());
                return toBidsDto(bidRepository.save(bid));
            }).orElseThrow(() -> new BidNotFound("Bid not found"));
    }

    public void deleteBid(UUID bidId) {
        bidRepository.deleteById(bidId);
    }

    public static Bid toEntity(BidsDto bidsDto, Auction auction) {
        return Bid.builder()
            .auction(auction)
            .product(auction.getProduct())
            .amount(bidsDto.bidAmount())
            .bidId(bidsDto.bidderId())
            .bidderId(bidsDto.bidderId())
            .build();
    }
}
