package com.ubbcluj.customer.repository;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderRequestJMSDto {
    protected Long orderId;
    protected Long restaurantId;
    protected Long customerId;
    protected List<Long> itemIds;
    protected Long finalPrice;
}
