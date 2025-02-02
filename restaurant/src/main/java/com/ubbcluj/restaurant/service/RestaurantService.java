package com.ubbcluj.restaurant.service;

import com.ubbcluj.restaurant.dtos.*;

import java.util.List;

public interface RestaurantService {
    RestaurantDto register(RestaurantCreateDto dto);
    ItemDto createItem(ItemCreateDto dto, Long requester);
    List<RestaurantDto> getAllRestaurants();
    List<ItemDto> getItemsForRestaurant(Long restaurantId);
    void deleteItem(Long itemId);
    ItemDto updateItem(Long itemId, ItemCreateDto dto);

    List<ItemDto> getAllItemsByIds(ItemIdsDto dto);
}
