package com.db.codingchallenge.auctionserver.controllers;

import com.db.codingchallenge.auctionserver.dtos.AuctionDto;
import com.db.codingchallenge.auctionserver.dtos.BidsDto;
import com.db.codingchallenge.auctionserver.dtos.CompletedAuctionEventResponseDto;
import com.db.codingchallenge.auctionserver.services.AuctionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuctionControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private AuctionService auctionService;

    @BeforeEach
    void setUp() {
    }


    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllAuctions() throws Exception {
        // Given
        AuctionDto auctionDto = mockAuction();
        when(auctionService.getAllAuctions()).thenReturn(Collections.singletonList(auctionDto));

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/auctions")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("[{}]")); // expecting a JSON array with a single empty object
    }

    @Test
    void getAuctionById() throws Exception {
        // Given
        UUID auctionId = UUID.randomUUID();
        AuctionDto auctionDto = mockAuction();
        when(auctionService.getAuctionById(auctionId)).thenReturn(Optional.of(auctionDto));

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/auctions/" + auctionId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{}")); // expecting a JSON object
    }

    @Test
    void createAuction() throws Exception {
        // Given
        AuctionDto auctionDto = mockAuction();
        when(auctionService.createAuction(any(AuctionDto.class))).thenReturn(auctionDto);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/auctions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(auctionDto)))
            .andExpect(status().isOk())
            .andExpect(content().json("{}")); // expecting a JSON object
    }

    @Test
    void updateAuction() throws Exception {
        // Given
        UUID auctionId = UUID.randomUUID();
        AuctionDto auctionDto = mockAuction();
        when(auctionService.updateAuction(eq(auctionId), any(AuctionDto.class))).thenReturn(Optional.of(auctionDto));

        // Then
        mockMvc.perform(MockMvcRequestBuilders.put("/auctions/" + auctionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(auctionDto)))
            .andExpect(status().isOk())
            .andExpect(content().json("{}")); // expecting a JSON object
    }

    @Test
    void deleteAuction() throws Exception {
        // Given
        UUID auctionId = UUID.randomUUID();
        doNothing().when(auctionService).deleteAuction(auctionId);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/auctions/" + auctionId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void getAllBids() throws Exception {
        // Given
        UUID auctionId = UUID.randomUUID();
        BidsDto bidsDto = BidsDto.builder().build();
        when(auctionService.getAllBids(auctionId)).thenReturn(Collections.singletonList(bidsDto));

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/auctions/" + auctionId + "/bids")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("[{}]")); // expecting a JSON array with a single empty object
    }

    @Test
    void completeAuction() throws Exception {
        // Given
        UUID auctionId = UUID.randomUUID();
        CompletedAuctionEventResponseDto responseDto = CompletedAuctionEventResponseDto.builder().build();
        when(auctionService.completeAuction(auctionId)).thenReturn(responseDto);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/auctions/" + auctionId + "/complete")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{}")); // expecting a JSON object
    }

    private AuctionDto mockAuction() {
        return AuctionDto.builder()
            .auctionId(UUID.randomUUID())
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
            .bids(Collections.singletonList(BidsDto.builder().build()))
            .build();
    }
}