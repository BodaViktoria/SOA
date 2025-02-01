package com.ubbcluj.authentication.config;

import com.ubbcluj.authentication.dto.TokenContents;
import com.ubbcluj.authentication.service.TokenUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
public class AuthorizationFilter implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (isAuthMissing(request)) {
            return onError(exchange, HttpStatus.UNAUTHORIZED);
        }

        String accessToken = getAuthHeader(request);
        TokenContents tokenData = TokenUtils.validateAccessToken(accessToken);

        if (tokenData == null) {
            return onError(exchange, HttpStatus.UNAUTHORIZED);
        }

        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                .header("username", tokenData.getUsername())
                .header("userId", tokenData.getUserId().toString())
                .build();

        System.out.println(tokenData.getUsername());
        System.out.println(tokenData.getUserId());

        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getFirst("Authorization");
    }
}
