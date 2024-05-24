package com.db.codingchallenge.auctionserver.services;

import com.db.codingchallenge.auctionserver.clients.UserServiceClient;
import com.db.codingchallenge.auctionserver.dtos.AuctionDto;
import com.db.codingchallenge.auctionserver.dtos.BidsDto;
import com.db.codingchallenge.auctionserver.entities.Auction;
import com.db.codingchallenge.auctionserver.entities.Bid;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.db.codingchallenge.auctionserver.testdata.MockData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        Auction auction = mockAuction();
        UUID auctionId = auction.getAuctionId();
        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auction));

        auctionService.deleteAuction(auctionId);

        verify(auctionRepository, times(1))
                .deleteById(auctionId);
    }

    @Test
    void testGetAllBids() {
        Auction auction = mockAuction();
        Product product = mockProduct();

        List<Bid> bids = List.of(mockBidEntity(auction, product), mockBidEntity(auction, product));
        auction.setBids(bids);

        when(auctionRepository.findById(any(UUID.class))).thenReturn(Optional.of(auction));

        List<BidsDto> result = auctionService.getAllBids(auction.getAuctionId());

        assertFalse(result.isEmpty());
        assertEquals(bids.size(), result.size());

        assertThat(result)
                .extracting(BidsDto::bidId)
                .containsAnyElementsOf(
                        bids.stream().map(Bid::getBidId).toList());
    }

    @Test
    void testCompleteAuction() {

        Auction auction = mockAuction();
        Product product = mockProduct();
        Bid winningBid = mockBidEntity(auction, product, 150.0);
        var bids = List.of(mockBidEntity(auction, product, 100.0), winningBid);
        auction.setProduct(product);
        auction.setBids(bids);

        when(auctionRepository.findById(any(UUID.class))).thenReturn(Optional.of(auction));

        var result = auctionService.completeAuction(auction.getAuctionId());

        assertNotNull(result);
        assertEquals(auction.getAuctionId(), result.auctionId());
        assertTrue(auction.getIsCompleted());
        assertEquals(150.0, auction.getWinningPrice());
        assertEquals(winningBid.getBidderId(), result.winningBid().bidderId());
    }
}