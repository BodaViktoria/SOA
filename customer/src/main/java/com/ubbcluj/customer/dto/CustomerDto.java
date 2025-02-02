package com.ubbcluj.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDto {
    protected Long id;
    protected String name;
    protected Integer rating;
}
