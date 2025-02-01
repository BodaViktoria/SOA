package com.ubbcluj.authentication.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ubbcluj.authentication.dto.Tokens;

import java.util.Date;

public abstract class TokenUtils {
    private static final String JWT_KEY = "RANv1a1Yd3a0uMoIn1Z/FxLTVx80cphf7eX48APILzI5uil8cNy7Oxt/jtipSNku";
    private static final Algorithm ALGORITHM = Algorithm.HMAC512(JWT_KEY);

    public static Tokens retrieveAccessToken(String username, Long userId) {
        return new Tokens(JWT.create()
                .withSubject(username)
                .withClaim("userId", userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + 300 * 60 * 1000))
                .sign(ALGORITHM));
    }
}