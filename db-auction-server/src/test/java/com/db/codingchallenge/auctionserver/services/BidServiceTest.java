package com.db.codingchallenge.auctionserver.services;

import com.db.codingchallenge.auctionserver.clients.UserServiceClient;
import com.db.codingchallenge.auctionserver.dtos.BidsDto;
import com.db.codingchallenge.auctionserver.entities.Auction;
import com.db.codingchallenge.auctionserver.entities.Bid;
import com.db.codingchallenge.auctionserver.entities.Product;
import com.db.codingchallenge.auctionserver.exceptions.*;
import com.db.codingchallenge.auctionserver.helpers.BidsValidationHelper;
import com.db.codingchallenge.auctionserver.repositories.BidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.db.codingchallenge.auctionserver.services.BidService.toEntity;
import static com.db.codingchallenge.auctionserver.testdata.MockData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BidServiceTest {

    @Mock
    private BidRepository bidRepository;

    @Mock
    private AuctionService auctionService;

    @Mock
    private UserServiceClient userServiceClient;

    @InjectMocks
    private BidsValidationHelper bidsValidationHelper;
    private BidService bidService;

    @BeforeEach
    void setUp() {
        bidService = new BidService(bidRepository, auctionService, bidsValidationHelper);
    }

    @Test
    void testGetAllBids() {
        Auction auction = mockAuction();
        Product product = mockProduct();
        Bid bid1 = mockBidEntity(auction, product, 100.0);
        Bid bid2 = mockBidEntity(auction, product, 120.0);

        List<Bid> bids = Arrays.asList(bid1, bid2);

        when(bidRepository.findAll()).thenReturn(bids);

        var result = bidService.getAllBids();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertThat(result)
                .extracting(BidsDto::bidId)
                .containsAnyElementsOf(
                        bids.stream().map(Bid::getBidId).toList());
    }

    @Test
    void testGetBidById() {
        UUID bidId = UUID.randomUUID();
        Auction auction = mockAuction();
        Product product = mockProduct();
        Bid expectedBid = mockBidEntity(auction, product, 100.0);
        when(bidRepository.findById(bidId)).thenReturn(Optional.of(expectedBid));

        var actualBid = bidService.getBidById(bidId);

        assertTrue(actualBid.isPresent());
        assertEquals(expectedBid, actualBid.get());
    }

    @Nested
    class CreateBidsTests {

        @Test
        void testCreateBid() {
            Product product = mockProduct();
            Auction auction = mockAuction(product);

            auction.setProduct(product);

            BidsDto bidsDto = mockBidDto(auction.getAuctionId(), product.getProductId(), auction.getMinPrice() + 20);
            Bid bid = toEntity(bidsDto, auction);
            auction.setBids(List.of(bid));

            when(auctionService.getAuction(bidsDto.auctionId())).thenReturn(Optional.of(auction));
            when(userServiceClient.checkBidderExists(bidsDto.bidderId())).thenReturn(Mono.just(true));
            when(bidRepository.save(any())).thenReturn(bid);

            var result = bidService.createBid(bidsDto);

            assertNotNull(result);
            assertEquals(bid.getBidId(), result.bidId());
        }

        @Test
        void testCreateBidWhenAuctionIsNotThere() {
            Product product = mockProduct();
            Auction auction = mockAuction(product);

            auction.setProduct(product);

            BidsDto bidsDto = mockBidDto(auction.getAuctionId(), product.getProductId(), auction.getMinPrice() + 20);
            Bid bid = toEntity(bidsDto, auction);
            auction.setBids(List.of(bid));

            when(auctionService.getAuction(bidsDto.auctionId())).thenReturn(Optional.empty());
            when(userServiceClient.checkBidderExists(bidsDto.bidderId())).thenReturn(Mono.just(false));
            when(bidRepository.save(any())).thenReturn(bid);

            assertThrows(AuctionNotFound.class, () -> bidService.createBid(bidsDto));
        }

        @Test
        void testCreateBidWhenBidderDoesNotExists() {
            Product product = mockProduct();
            Auction auction = mockAuction(product);

            auction.setProduct(product);

            BidsDto bidsDto = mockBidDto(auction.getAuctionId(), product.getProductId());
            Bid bid = toEntity(bidsDto, auction);
            auction.setBids(List.of(bid));

            when(auctionService.getAuction(bidsDto.auctionId())).thenReturn(Optional.of(auction));
            when(userServiceClient.checkBidderExists(bidsDto.bidderId())).thenReturn(Mono.just(false));
            when(bidRepository.save(any())).thenReturn(bid);

            assertThrows(BidderNotFound.class, () -> bidService.createBid(bidsDto));
        }

        @Test
        void testCreateBidWhenBidderBiddingForWrongProductThatDoesNotExistsInAuction() {
            Product product = mockProduct();
            Auction auction = mockAuction(product);

            auction.setProduct(product);

            BidsDto bidsDto = mockBidDto(auction.getAuctionId(), UUID.randomUUID());
            Bid bid = toEntity(bidsDto, auction);
            auction.setBids(List.of(bid));

            when(auctionService.getAuction(bidsDto.auctionId())).thenReturn(Optional.of(auction));
            when(userServiceClient.checkBidderExists(bidsDto.bidderId())).thenReturn(Mono.just(true));
            when(bidRepository.save(any())).thenReturn(bid);

            assertThrows(ProductNotFound.class, () -> bidService.createBid(bidsDto));
        }

        @Test
        void testCreateBidWhenBidderNotBiddingMinimumPrice() {
            Product product = mockProduct();
            Auction auction = mockAuction(product);

            auction.setProduct(product);

            BidsDto bidsDto = mockBidDto(auction.getAuctionId(), product.getProductId());
            Bid bid = toEntity(bidsDto, auction);
            auction.setBids(List.of(bid));

            when(auctionService.getAuction(bidsDto.auctionId())).thenReturn(Optional.of(auction));
            when(userServiceClient.checkBidderExists(bidsDto.bidderId())).thenReturn(Mono.just(true));
            when(bidRepository.save(any())).thenReturn(bid);

            assertThrows(AuctionShouldHaveHigherBidAmount.class, () -> bidService.createBid(bidsDto));
        }

        @Test
        void testCreateBidWhenBiddingForAuctionThatDoesNotStartedYet() {
            Product product = mockProduct();
            Auction auction = mockAuction(product);
            auction.setStartDate(Instant.now().plus(1, ChronoUnit.DAYS));

            auction.setProduct(product);

            BidsDto bidsDto = mockBidDto(auction.getAuctionId(), product.getProductId(), auction.getMinPrice() + 20);
            Bid bid = toEntity(bidsDto, auction);
            auction.setBids(List.of(bid));

            when(auctionService.getAuction(bidsDto.auctionId())).thenReturn(Optional.of(auction));
            when(userServiceClient.checkBidderExists(bidsDto.bidderId())).thenReturn(Mono.just(true));
            when(bidRepository.save(any())).thenReturn(bid);

            assertThrows(BidNotPossible.class, () -> bidService.createBid(bidsDto));
        }

        @Test
        void testCreateBidWhenBiddingForAuctionWhoseEndDateIsInPast() {
            Product product = mockProduct();
            Auction auction = mockAuction(product);
            auction.setEndDate(Instant.now().minus(1, ChronoUnit.DAYS));

            auction.setProduct(product);

            BidsDto bidsDto = mockBidDto(auction.getAuctionId(), product.getProductId(), auction.getMinPrice() + 20);
            Bid bid = toEntity(bidsDto, auction);
            auction.setBids(List.of(bid));

            when(auctionService.getAuction(bidsDto.auctionId())).thenReturn(Optional.of(auction));
            when(userServiceClient.checkBidderExists(bidsDto.bidderId())).thenReturn(Mono.just(true));
            when(bidRepository.save(any())).thenReturn(bid);

            assertThrows(BidNotPossible.class, () -> bidService.createBid(bidsDto));
        }

        @Test
        void testCreateBidWhenBiddingForTheAuctionThatIsCompleted() {
            Product product = mockProduct();
            Auction auction = mockAuction(product);
            auction.setIsCompleted(true);

            auction.setProduct(product);

            BidsDto bidsDto = mockBidDto(auction.getAuctionId(), product.getProductId(), auction.getMinPrice() + 20);
            Bid bid = toEntity(bidsDto, auction);
            auction.setBids(List.of(bid));

            when(auctionService.getAuction(bidsDto.auctionId())).thenReturn(Optional.of(auction));
            when(userServiceClient.checkBidderExists(bidsDto.bidderId())).thenReturn(Mono.just(true));
            when(bidRepository.save(any())).thenReturn(bid);

            assertThrows(BidNotPossible.class, () -> bidService.createBid(bidsDto));
        }
    }

    @Test
    void testUpdateBidAmount() {
        UUID bidId = UUID.randomUUID();
        Auction auction = mockAuction();
        Product product = mockProduct();

        Bid bid = mockBidEntity(auction, product, 100.0);
        BidsDto bidsDto = mockBidDto(auction.getAuctionId(), product.getProductId(), 120.0);
        Bid updateBid = mockBidEntity(bidId, auction, product, 120.0);

        when(bidRepository.findById(bidId)).thenReturn(Optional.of(bid));
        when(bidRepository.save(any())).thenReturn(updateBid);

        var result = bidService.updateBid(bidId, bidsDto);

        assertNotNull(result);
        assertEquals(updateBid.getBidId(), result.bidId());
        assertEquals(updateBid.getAmount(), result.bidAmount());
    }

    @Test
    void testDeleteBid() {
        UUID bidId = UUID.randomUUID();
        Auction auction = mockAuction();
        Product product = mockProduct();

        Bid bid = mockBidEntity(bidId, auction, product, 100.0);
        when(bidRepository.findById(bidId)).thenReturn(Optional.of(bid));

        bidService.deleteBid(bidId);

        verify(bidRepository, times(1))
                .deleteById(bidId);
    }
}