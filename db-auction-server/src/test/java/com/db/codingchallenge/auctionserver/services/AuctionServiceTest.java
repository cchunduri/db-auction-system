package com.db.codingchallenge.auctionserver.services;

import com.db.codingchallenge.auctionserver.clients.UserServiceClient;
import com.db.codingchallenge.auctionserver.dtos.AuctionDto;
import com.db.codingchallenge.auctionserver.dtos.BidsDto;
import com.db.codingchallenge.auctionserver.entities.Auction;
import com.db.codingchallenge.auctionserver.entities.Product;
import com.db.codingchallenge.auctionserver.exceptions.ProductNotFound;
import com.db.codingchallenge.auctionserver.exceptions.SellerNotFound;
import com.db.codingchallenge.auctionserver.repositories.AuctionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuctionServiceTest {

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private ProductService productService;

    @Mock
    private UserServiceClient userServiceClient;

    @InjectMocks
    private AuctionService auctionService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testGetAllAuctions() {
        Auction auction = mockAuction();
        when(auctionRepository.findAll()).thenReturn(List.of(auction));

        List<AuctionDto> result = auctionService.getAllAuctions();

        assertFalse(result.isEmpty());
        assertEquals(auction.getAuctionId(), result.getFirst().auctionId());
    }

    @Test
    void testGetAuctionById() {
        UUID id = UUID.randomUUID();
        Auction auction = mockAuction();
        when(auctionRepository.findById(any(UUID.class))).thenReturn(Optional.of(auction));

        Optional<Auction> result = auctionService.getAuction(id);

        assertTrue(result.isPresent());
        assertEquals(auction.getAuctionId(), result.get().getAuctionId());
    }

    @Test
    void testCreateAuction() {
        var auctionDto = mockAuctionDto();

        var product = mockProduct();
        var auction = auctionService.toEntity(auctionDto, product);

        when(productService.getProductById(any(UUID.class))).thenReturn(Optional.of(product));
        when(userServiceClient.checkSellerExists(any(UUID.class))).thenReturn(Mono.just(true));
        when(auctionRepository.save(any(Auction.class))).thenReturn(auction);

        AuctionDto result = auctionService.createAuction(auctionDto);

        assertNotNull(result);
        assertEquals(auction.getAuctionId(), result.auctionId());
    }

    @Test
    void testCreateAuctionIfSellerExists() {
        var auctionDto = mockAuctionDto();

        var product = mockProduct();
        var auction = auctionService.toEntity(auctionDto, product);

        when(productService.getProductById(any(UUID.class))).thenReturn(Optional.of(product));
        when(userServiceClient.checkSellerExists(any(UUID.class))).thenReturn(Mono.just(false));
        when(auctionRepository.save(any(Auction.class))).thenReturn(auction);

        assertThrows(SellerNotFound.class, () -> auctionService.createAuction(auctionDto));
    }

    @Test
    void testCreateAuctionIfProductDoesNotExists() {
        var auctionDto = mockAuctionDto();

        var product = mockProduct();
        var auction = auctionService.toEntity(auctionDto, product);

        when(productService.getProductById(any(UUID.class))).thenThrow(new ProductNotFound("Product not found"));
        when(userServiceClient.checkSellerExists(any(UUID.class))).thenReturn(Mono.just(false));
        when(auctionRepository.save(any(Auction.class))).thenReturn(auction);

        assertThrows(ProductNotFound.class, () -> auctionService.createAuction(auctionDto));
    }

    @Test
    void testUpdateAuction() {
        var auctionDto = mockAuctionDto();
        var product = mockProduct();

        var auction = auctionService.toEntity(auctionDto, product);

        when(auctionRepository.findById(any(UUID.class))).thenReturn(Optional.of(auction));
        when(auctionRepository.save(any(Auction.class))).thenReturn(auction);

        Optional<AuctionDto> result = auctionService.updateAuction(auctionDto.auctionId(), auctionDto);

        assertTrue(result.isPresent());
        assertEquals(auction.getAuctionId(), result.get().auctionId());
    }

    @Test
    void testDeleteAuction() {

    }

    @Test
    void testGetAllBids() {

    }

    @Test
    void testCompleteAuction() {

    }

    private Product mockProduct() {
        return Product.builder()
                .productId(UUID.randomUUID())
                .name("Test Product")
                .description("This is a test product")
                .build();
    }

    private Auction mockAuction() {
        return Auction.builder()
                    .auctionId(UUID.randomUUID())
                    .name("Test Auction")
                    .description("This is a test auction")
                    .minPrice(100.0)
                    .startDate(Instant.now())
                    .endDate(Instant.now().plusSeconds(86400)) // plus 1 day
                    .sellerId(UUID.randomUUID())
                    .auctionWinnerId(UUID.randomUUID())
                    .isCompleted(false)
                    .product(mockProduct())
                    .bids(null)
                .build();
    }

    private AuctionDto mockAuctionDto() {
        var auctionId = UUID.randomUUID();
        return AuctionDto.builder()
                    .auctionId(auctionId)
                    .name("Test Auction")
                    .description("This is a test auction")
                    .productId(UUID.randomUUID())
                    .minPrice(100.0)
                    .winningPrice(200.0)
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now().plusDays(1))
                    .sellerId(UUID.randomUUID())
                    .auctionWinnerId(UUID.randomUUID())
                    .isCompleted(false)
                    .bids(Collections.singletonList(mockBid(auctionId)))
                .build();
    }

    private static BidsDto mockBid(UUID auctionId) {
        return BidsDto.builder()
                    .bidId(UUID.randomUUID())
                    .auctionId(auctionId)
                    .bidderId(UUID.randomUUID())
                    .productId(UUID.randomUUID())
                    .bidAmount(100.0)
                .build();
    }
}