package com.ubbcluj.authentication.controller;

import com.ubbcluj.authentication.dto.LoginCredentials;
import com.ubbcluj.authentication.dto.RegisterDto;
import com.ubbcluj.authentication.dto.Tokens;
import com.ubbcluj.authentication.dto.UserDto;
import com.ubbcluj.authentication.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class LoginController {

    protected final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Tokens> login(@RequestBody LoginCredentials dto){
        return ResponseEntity.ok().body(userService.login(dto));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterDto dto){
        return ResponseEntity.ok().body(userService.register(dto));
    }
}
