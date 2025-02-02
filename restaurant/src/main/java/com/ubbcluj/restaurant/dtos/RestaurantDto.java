package com.ubbcluj.restaurant.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantDto {
    protected Long id;
    protected Long associatedUserId;
    protected String name;
    protected String description;
}
