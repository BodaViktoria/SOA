package com.ubbcluj.customer.repository;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ItemIdsDto {
    protected List<Long> itemIds;
}
