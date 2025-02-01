package com.ubbcluj.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    protected Long id;
    protected String username;
}
