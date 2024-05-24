package com.db.codingchallenge.auctionserver.controllers;

import com.db.codingchallenge.auctionserver.dtos.ApiMessage;
import com.db.codingchallenge.auctionserver.dtos.BidsDto;
import com.db.codingchallenge.auctionserver.entities.Bid;
import com.db.codingchallenge.auctionserver.services.BidService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bids")
@AllArgsConstructor
public class BidsController {

    private BidService bidService;

    @GetMapping
    public List<BidsDto> getAllBids() {
        return bidService.getAllBids();
    }

    @GetMapping("/{bidId}")
    public ResponseEntity<Bid> getBidById(@PathVariable UUID bidId) {
        return bidService.getBidById(bidId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public BidsDto createBid(@RequestBody BidsDto bidsDto) {
        return bidService.createBid(bidsDto);
    }

    @PutMapping("/{bidId}")
    public BidsDto updateBid(@PathVariable UUID bidId, @RequestBody BidsDto bidsDto) {
        return bidService.updateBid(bidId, bidsDto);
    }

    @DeleteMapping("/{bidId}")
    public ResponseEntity<ApiMessage> deleteBid(@PathVariable UUID bidId) {
        bidService.deleteBid(bidId);
        return ResponseEntity.ok().build();
    }
}
