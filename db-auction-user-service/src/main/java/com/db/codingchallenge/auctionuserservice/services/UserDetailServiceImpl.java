package com.db.codingchallenge.auctionuserservice.services;

import com.db.codingchallenge.auctionuserservice.entities.AuctionUser;
import com.db.codingchallenge.auctionuserservice.repositories.AuctionUserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private AuctionUserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AuctionUser> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            logger.error("Username not found: {}", username);
            throw new UsernameNotFoundException("User doesn't exists..!!");
        }

        var user = userOpt.get();
        logger.debug("User Authenticated Successfully..!!!");
        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRole().name())))
            .build();
    }
}