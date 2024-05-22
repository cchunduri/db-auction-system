package com.db.codingchallenge.auctionuserservice.controllers;

import com.db.codingchallenge.auctionuserservice.dtos.ApiResponse;
import com.db.codingchallenge.auctionuserservice.dtos.AuctionUserDto;
import com.db.codingchallenge.auctionuserservice.dtos.JwtResponseDTO;
import com.db.codingchallenge.auctionuserservice.dtos.LoginRequestDto;
import com.db.codingchallenge.auctionuserservice.services.JwtService;
import com.db.codingchallenge.auctionuserservice.services.UsersService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {

    private JwtService jwtService;
    private UsersService usersService;
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String hello() {
        return "Welcome to School Manager API";
    }

    @PostMapping("/register")
    public ApiResponse registerUser(@RequestBody AuctionUserDto userRegistrationRequest) {
        usersService.registerUser(userRegistrationRequest);
        return new ApiResponse("Registration Success");
    }

    @PostMapping("/login")
    public JwtResponseDTO loginUser(@RequestBody LoginRequestDto loginRequest) {
        var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );
        if(authentication.isAuthenticated()){
            return new JwtResponseDTO(jwtService.generateToken(loginRequest.username()));
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @GetMapping("/all")
    public List<AuctionUserDto> getAllUsers() {
        return usersService.getAllUsers();
    }
}