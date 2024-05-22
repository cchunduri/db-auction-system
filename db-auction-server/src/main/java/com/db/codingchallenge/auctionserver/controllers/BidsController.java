package com.db.codingchallenge.auctionserver.controllers;

import com.db.codingchallenge.auctionserver.dtos.BidsDto;
import com.db.codingchallenge.auctionserver.entities.Bid;
import com.db.codingchallenge.auctionserver.services.BidService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public BidsDto createBid(@RequestBody BidsDto bidCreateDTO) {
        return bidService.createBid(bidCreateDTO);
    }

    @PutMapping("/{bidId}")
    public BidsDto updateBid(@PathVariable UUID bidId, @RequestBody BidsDto bidsDto) {
        return bidService.updateBid(bidId, bidsDto);
    }

    @DeleteMapping("/{bidId}")
    public ResponseEntity<?> deleteBid(@PathVariable UUID bidId) {
        bidService.deleteBid(bidId);
        return ResponseEntity.ok().build();
    }
}
