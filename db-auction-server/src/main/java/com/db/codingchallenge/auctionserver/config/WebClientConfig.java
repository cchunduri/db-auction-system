package com.db.codingchallenge.auctionserver.config;

import com.db.codingchallenge.auctionserver.properties.UserServiceProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@AllArgsConstructor
public class WebClientConfig {

    private UserServiceProperties userServiceProperties;

    @Bean
    public WebClient webClient() {
        return WebClient.create(userServiceProperties.getUrl());
    }
}
