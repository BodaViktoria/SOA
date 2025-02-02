package com.ubbcluj.restaurant.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestJMSDto {
    protected Long orderId;
    protected Long restaurantId;
    protected Long customerId;
    protected List<Long> itemIds;
    protected Long finalPrice;
}
