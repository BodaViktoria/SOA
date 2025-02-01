package com.ubbcluj.authentication.service;

import com.ubbcluj.authentication.dto.LoginCredentials;
import com.ubbcluj.authentication.dto.RegisterDto;
import com.ubbcluj.authentication.dto.Tokens;
import com.ubbcluj.authentication.dto.UserDto;

public interface UserService {
    Tokens login(LoginCredentials dto);
    UserDto register(RegisterDto dto);
}
