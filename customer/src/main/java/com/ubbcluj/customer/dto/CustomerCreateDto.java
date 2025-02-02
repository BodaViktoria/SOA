package com.ubbcluj.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerCreateDto {
    protected String name;
    protected String username;
    protected String password;
}
