package com.ubbcluj.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenContents {
    protected String username;
    protected Long userId;
}
