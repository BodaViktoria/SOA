package com.ubbcluj.restaurant.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    protected Long id;
    protected String name;
    protected String description;
    protected Long price;
    protected Long restaurantId;
}
