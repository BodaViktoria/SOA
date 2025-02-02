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

    @Value("${customerServiceUrl}")
    protected String customerServiceUrl;

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
                .route(p ->
                        p.method(HttpMethod.GET)
                                .and()
                                .path("/restaurant/api/restaurants")
                                .uri(restaurantServiceUrl)
                )
                .route(p ->
                        p.method(HttpMethod.GET)
                                .and()
                                .path("/restaurant/api/item/restaurant/{restaurantId}")
                                .uri(restaurantServiceUrl)
                )
                .route(p ->
                        p.method(HttpMethod.DELETE)
                                .and()
                                .path("/restaurant/api/item/{itemId}")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri(restaurantServiceUrl)
                )
                .route(p ->
                        p.method(HttpMethod.PUT)
                                .and()
                                .path("/restaurant/api/item/{itemId}")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri(restaurantServiceUrl)
                )
                .route(
                        p ->
                                p.method(HttpMethod.POST)
                                        .and()
                                        .path(
                                                "/customer/api/register"
                                        )
                                        .uri(customerServiceUrl))
                .route(
                        p ->
                                p.method(HttpMethod.POST)
                                        .and()
                                        .path(
                                                "/restaurant/api/item/itemList"
                                        )
                                        .uri(restaurantServiceUrl))
                .route(
                        p ->
                                p.method(HttpMethod.POST)
                                        .and()
                                        .path(
                                                "/customer/api/order"
                                        )
                                        .filters(f -> f.filter(authorizationFilter))
                                        .uri(customerServiceUrl))
                .build();
    }
}
