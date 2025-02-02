package com.ubbcluj.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateOrderDto {
    protected List<Long> itemIds;
}
