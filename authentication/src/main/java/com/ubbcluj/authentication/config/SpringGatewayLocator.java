package com.ubbcluj.authentication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class SpringGatewayLocator {
    protected final AuthorizationFilter authorizationFilter;

    @Value("${restaurantServiceUrl}")
    protected String restaurantServiceUrl;

    public SpringGatewayLocator(AuthorizationFilter authorizationFilter) {
        this.authorizationFilter = authorizationFilter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route(
                p ->
                        p.method(HttpMethod.POST)
                                        .and()
                                        .path(
                                                "/restaurant/api/register"
                                                )
                                        .uri(restaurantServiceUrl))
                .route(
                p ->
                        p.method(HttpMethod.POST)
                                .and()
                                .path(
                                        "/restaurant/api/item"
                                ).filters(f -> f.filter(authorizationFilter))
                                .uri(restaurantServiceUrl))
                .build();
    }
}
