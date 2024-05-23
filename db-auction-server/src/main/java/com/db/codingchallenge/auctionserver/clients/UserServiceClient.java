package com.db.codingchallenge.auctionserver.clients;

import com.db.codingchallenge.auctionserver.exceptions.BidderNotFound;
import com.db.codingchallenge.auctionserver.exceptions.SellerNotFound;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class UserServiceClient {

    private final WebClient webClient;

    public Mono<Boolean> checkSellerExists(UUID sellerId) {
        return webClient.get()
            .uri("/sellers/{sellerId}", sellerId)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new SellerNotFound("Bidder not found")))
            .bodyToMono(String.class)
            .map(body -> true)
            .onErrorReturn(false);
    }

    public Mono<Boolean> checkBidderExists(UUID bidderId) {
        return webClient.get()
            .uri("/bidders/{bidderId}", bidderId)
            .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new BidderNotFound("Bidder not found")))
            .bodyToMono(String.class)
            .map(body -> true);
    }
}
