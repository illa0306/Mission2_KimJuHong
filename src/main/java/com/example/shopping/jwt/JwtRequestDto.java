package com.example.shopping.jwt;

// annotation 결정 필요

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequestDto {
    private String username;
    private String password;
}
