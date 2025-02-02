package com.ubbcluj.restaurant.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemDto {
    protected Long id;
    protected String name;
    protected String description;
    protected Long price;
}
