package com.ubbcluj.restaurant.controller;

import com.ubbcluj.restaurant.dtos.ItemCreateDto;
import com.ubbcluj.restaurant.dtos.ItemDto;
import com.ubbcluj.restaurant.dtos.ItemIdsDto;
import com.ubbcluj.restaurant.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@CrossOrigin
public class ItemController {

    protected final RestaurantService restaurantService;

    public ItemController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<ItemDto> create(@RequestBody ItemCreateDto dto, @RequestHeader("userId") Long requester){
        return ResponseEntity.ok().body(restaurantService.createItem(dto, requester));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<ItemDto>> getItemsForRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok().body(restaurantService.getItemsForRestaurant(restaurantId));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        restaurantService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable Long itemId, @RequestBody ItemCreateDto dto) {
        return ResponseEntity.ok().body(restaurantService.updateItem(itemId, dto));
    }

    @PostMapping("/itemList")
    public ResponseEntity<List<ItemDto>> getAllItemsByIds(@RequestBody ItemIdsDto dto){
        return ResponseEntity.ok().body(restaurantService.getAllItemsByIds(dto));
    }
}
