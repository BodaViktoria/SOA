package com.ubbcluj.customer.repository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    protected Long id;
    protected String username;
}
