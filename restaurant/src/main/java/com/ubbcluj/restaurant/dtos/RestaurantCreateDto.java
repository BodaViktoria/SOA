package com.ubbcluj.restaurant.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantCreateDto {
    protected String name;
    protected String description;
    protected String username;
    protected String password;
}
