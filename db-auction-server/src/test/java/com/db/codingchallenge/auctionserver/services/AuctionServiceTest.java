package com.db.codingchallenge.auctionserver.services;

import com.db.codingchallenge.auctionserver.clients.UserServiceClient;
import com.db.codingchallenge.auctionserver.dtos.AuctionDto;
import com.db.codingchallenge.auctionserver.entities.Auction;
import com.db.codingchallenge.auctionserver.entities.Product;
import com.db.codingchallenge.auctionserver.repositories.AuctionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
//        Auction auction = mockAuction();
//        AuctionDto auctionDto = AuctionDto.builder()
//                .auctionId(auction.getAuctionId())
//                .name(auction.getName())
//                .description(auction.getDescription())
//                .minPrice(auction.getMinPrice())
//                .startDate(auction.getStartDate())
//                .endDate(auction.getEndDate())
//                .sellerId(auction.getSellerId())
//                .auctionWinnerId(auction.getAuctionWinnerId())
//                .isCompleted(auction.isCompleted())
//                .product(Product.builder().productId(auction.getProduct().getProductId()).build())
//                .bids(null)
//                .build();
//        when(productService.getProductById(any(UUID.class))).thenReturn(Optional.of(auction.getProduct()));
//        when(userServiceClient.checkSellerExists(any(UUID.class))).thenReturn(null);
//        when(auctionRepository.save(any(Auction.class))).thenReturn(auction);
//
//        AuctionDto result = auctionService.createAuction(auctionDto);
//
//        assertNotNull(result);
//        assertEquals(auction.getAuctionId(), result.auctionId());
    }

    @Test
    void updateAuction() {
    }

    @Test
    void deleteAuction() {
    }

    @Test
    void getAllBids() {
    }

    @Test
    void completeAuction() {
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
                .product(Product.builder().productId(UUID.randomUUID()).build())
                .bids(null)
                .build();
    }
}