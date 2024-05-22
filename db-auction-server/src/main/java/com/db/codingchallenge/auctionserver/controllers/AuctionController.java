package com.db.codingchallenge.auctionserver.controllers;

import com.db.codingchallenge.auctionserver.dtos.AuctionDto;
import com.db.codingchallenge.auctionserver.services.AuctionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auctions")
@AllArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    @GetMapping
    public List<AuctionDto> getAllAuctions() {
        return auctionService.getAllAuctions();
    }

    @GetMapping("/{auctionId}")
    public ResponseEntity<AuctionDto> getAuctionById(@PathVariable UUID auctionId) {
        return auctionService.getAuctionById(auctionId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AuctionDto createAuction(@RequestBody AuctionDto auctionDto) {
        return auctionService.createAuction(auctionDto);
    }

    @PutMapping("/{auctionId}")
    public ResponseEntity<AuctionDto> updateAuction(@PathVariable UUID auctionId, @RequestBody AuctionDto auctionDto) {
        return auctionService.updateAuction(auctionId, auctionDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{auctionId}")
    public ResponseEntity<?> deleteAuction(@PathVariable UUID auctionId) {
        auctionService.deleteAuction(auctionId);
        return ResponseEntity.ok().build();
    }
}
