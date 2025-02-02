package com.ubbcluj.restaurant.repository;

import java.util.List;

public interface ItemRepository extends BaseRepository<Item, Long>{
    List<Item> findByRestaurantId(Long restaurantId);
    List<Item> findByIdIn(List<Long> ids);
}
