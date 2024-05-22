package com.db.codingchallenge.auctionuserservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private Long tokenExpiry;
    private String secretKey;
}