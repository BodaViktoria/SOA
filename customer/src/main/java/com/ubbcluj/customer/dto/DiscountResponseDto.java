package com.ubbcluj.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountResponseDto {
    protected Long original_price;
    protected Long discount;
    protected Long final_price;
}
