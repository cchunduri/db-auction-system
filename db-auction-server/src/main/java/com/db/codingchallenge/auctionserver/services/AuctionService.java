package com.db.codingchallenge.auctionserver.services;

import com.db.codingchallenge.auctionserver.dtos.AuctionDto;
import com.db.codingchallenge.auctionserver.entities.Auction;
import com.db.codingchallenge.auctionserver.entities.Product;
import com.db.codingchallenge.auctionserver.exceptions.ProductNotFound;
import com.db.codingchallenge.auctionserver.repositories.AuctionRepository;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final ProductService productService;

    public List<AuctionDto> getAllAuctions() {
        return auctionRepository.findAll().stream()
            .map(this::toDto)
            .toList();
    }

    public Optional<AuctionDto> getAuctionById(UUID id) {
        return auctionRepository.findById(id)
            .map(this::toDto);
    }

    public Optional<Auction> getAuction(UUID id) {
        return auctionRepository.findById(id);
    }

    public AuctionDto createAuction(AuctionDto auctionDto) {
        var product = productService.getProductById(auctionDto.productId())
            .orElseThrow(() -> new ProductNotFound("Product not found"));

        Auction auction = toEntity(auctionDto, product);
        Auction savedAuction = auctionRepository.save(auction);
        return toDto(savedAuction);
    }

    public Optional<AuctionDto> updateAuction(
        UUID id, AuctionDto auctionUpdateDTO
    ) {
        return auctionRepository.findById(id)
            .map(auction -> {
                toEntity(auctionUpdateDTO, auction.getProduct());
                Auction updatedAuction = auctionRepository.save(auction);
                return toDto(updatedAuction);
            });
    }

    public void deleteAuction(UUID id) {
        auctionRepository.deleteById(id);
    }

    public AuctionDto toDto(Auction auction) {
        return AuctionDto.builder()
            .auctionId(auction.getAuctionId())
            .name(auction.getName())
            .description(auction.getDescription())
            .productId(auction.getProduct().getProductId())
            .minPrice(auction.getMinPrice())
            .winningPrice(auction.getWinningPrice())
            .startDate(auction.getStartDate().atZone(ZoneId.systemDefault()).toLocalDateTime())
            .endDate(auction.getEndDate().atZone(ZoneId.systemDefault()).toLocalDateTime())
            .sellerId(auction.getSellerId())
            .auctionWinnerId(auction.getAuctionWinnerId())
            .isCompleted(auction.getIsCompleted())
            .build();
    }

    public Auction toEntity(
        AuctionDto auctionDto,
        Product product
    ) {
        return Auction.builder()
            .auctionId(auctionDto.auctionId())
            .name(auctionDto.name())
            .description(auctionDto.description())
            .product(product)
            .minPrice(auctionDto.minPrice())
            .winningPrice(auctionDto.winningPrice())
            .startDate(auctionDto.startDate().atZone(ZoneId.systemDefault()).toInstant())
            .endDate(auctionDto.endDate().atZone(ZoneId.systemDefault()).toInstant())
            .sellerId(auctionDto.sellerId())
            .auctionWinnerId(auctionDto.auctionWinnerId())
            .isCompleted(auctionDto.isCompleted())
            .build();
    }
}