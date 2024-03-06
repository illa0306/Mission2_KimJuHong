package com.example.shopping.controller;

import com.example.shopping.jwt.JwtRequestDto;
import com.example.shopping.jwt.JwtResponseDto;
import com.example.shopping.jwt.JwtTokenUtils;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("token")

public class TokenController {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/issue")
    public JwtResponseDto issueJwt(
            @RequestBody JwtRequestDto dto
    ) {
        if (!userDetailsManager.userExists(dto.getUsername()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        UserDetails userDetails
                = userDetailsManager.loadUserByUsername(dto.getUsername());

        if (!passwordEncoder
                .matches(dto.getPassword(), userDetails.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        String jwt = jwtTokenUtils.generateToken(userDetails);
        JwtResponseDto response = new JwtResponseDto(toString());
        response.setToken(jwt);
        return response;
    }


    public TokenController(
        JwtTokenUtils jwtTokenUtils,
        UserDetailsManager userDetailsManager,
        PasswordEncoder passwordEncoder
    ) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }
}
