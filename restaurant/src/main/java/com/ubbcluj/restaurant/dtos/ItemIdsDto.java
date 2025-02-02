package com.ubbcluj.restaurant.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ItemIdsDto {
    protected List<Long> itemIds;
}
