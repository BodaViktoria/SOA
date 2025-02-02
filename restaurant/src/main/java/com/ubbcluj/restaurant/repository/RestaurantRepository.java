package com.ubbcluj.restaurant.repository;


import java.util.Optional;

public interface RestaurantRepository extends BaseRepository<Restaurant, Long>{
    Optional<Restaurant> findByAssociatedUserId(Long id);
}
