package com.db.codingchallenge.auctionserver.clients;

import com.db.codingchallenge.auctionserver.exceptions.BidderNotFound;
import com.db.codingchallenge.auctionserver.exceptions.SellerNotFound;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Client to communicate with user service to check if seller exists and bidder exists.
 * Calls will route through gateway service. As we are using gateway service,
 * auction service will not have direct dependency on user service.
 */
@Component
@AllArgsConstructor
public class UserServiceClient {

    private final WebClient webClient;

    /**
     * Checks if the seller exists by calling user-service
     */
    public Mono<Boolean> checkSellerExists(UUID sellerId) {
        return webClient.get()
            .uri("/sellers/{sellerId}", sellerId)
            .retrieve()
            .onStatus(
                    HttpStatusCode::is4xxClientError,
                    clientResponse -> Mono.error(new SellerNotFound("Bidder not found"))
            )
            .bodyToMono(String.class)
            .map(body -> true)
            .onErrorReturn(false);
    }

    /**
     * Checks if the bidder exists by calling user-service
     */
    public Mono<Boolean> checkBidderExists(UUID bidderId) {
        return webClient.get()
            .uri("/bidders/{bidderId}", bidderId)
            .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> Mono.error(new BidderNotFound("Bidder not found"))
                )
            .bodyToMono(String.class)
            .map(body -> true);
    }
}
