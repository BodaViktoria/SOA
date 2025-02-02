package com.ubbcluj.customer.repository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterDto {
    protected String username;
    protected String password;
}
