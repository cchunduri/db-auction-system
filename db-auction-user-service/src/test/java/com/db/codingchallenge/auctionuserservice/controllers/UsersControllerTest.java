package com.db.codingchallenge.auctionuserservice.controllers;

import com.db.codingchallenge.auctionuserservice.dtos.ApiResponse;
import com.db.codingchallenge.auctionuserservice.services.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsersService usersService;

    @Test
    void testCheckSellerExists() throws Exception {
        // Given
        UUID sellerId = UUID.randomUUID();

        when(usersService.checkSellerExists(sellerId)).thenReturn(true);
        var apiResponse = new ApiResponse("Seller Exists");
        var expectedResponse = objectMapper.writeValueAsString(apiResponse);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/sellers/" + sellerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void testCheckSellerDoesNotExists() throws Exception {
        // Given
        UUID sellerId = UUID.randomUUID();

        when(usersService.checkSellerExists(sellerId)).thenReturn(false);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/sellers/" + sellerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCheckBidderExists() throws Exception {
        // Given
        UUID bidderId = UUID.randomUUID();

        when(usersService.checkBidderExists(bidderId)).thenReturn(true);
        var apiResponse = new ApiResponse("Bidder Exists");
        var expectedResponse = objectMapper.writeValueAsString(apiResponse);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/bidders/" + bidderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void testCheckBidderDoesNotExists() throws Exception {
        // Given
        UUID bidderId = UUID.randomUUID();

        when(usersService.checkBidderExists(bidderId)).thenReturn(false);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/bidders/" + bidderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}