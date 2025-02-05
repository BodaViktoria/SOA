package com.ubbcluj.restaurant.controller;

import com.ubbcluj.restaurant.dtos.RestaurantCreateDto;
import com.ubbcluj.restaurant.dtos.RestaurantDto;
import com.ubbcluj.restaurant.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestaurantController {
    protected final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/register")
    ResponseEntity<RestaurantDto> register(@RequestBody RestaurantCreateDto dto){
        return ResponseEntity.ok().body(restaurantService.register(dto));
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants(){
        return ResponseEntity.ok().body(restaurantService.getAllRestaurants());
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
