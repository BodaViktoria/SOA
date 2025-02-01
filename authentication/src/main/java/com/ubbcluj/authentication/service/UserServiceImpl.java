package com.ubbcluj.authentication.service;

import com.ubbcluj.authentication.dto.LoginCredentials;
import com.ubbcluj.authentication.dto.RegisterDto;
import com.ubbcluj.authentication.dto.Tokens;
import com.ubbcluj.authentication.dto.UserDto;
import com.ubbcluj.authentication.repository.UserEntity;
import com.ubbcluj.authentication.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    protected final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Tokens login(LoginCredentials dto) {
        var user = userRepository.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if(user.isEmpty()){
            throw new RuntimeException("Bad credentials!");
        }
        return TokenUtils.retrieveAccessToken(user.get().getUsername(), user.get().getId());
    }

    @Override
    public UserDto register(RegisterDto dto) {
        if(userRepository.existsByUsername(dto.getUsername())){
            throw new RuntimeException("Username already exists!");
        }
        var user = userRepository.save(new UserEntity(dto.getUsername(), dto.getPassword()));
        return new UserDto(user.getId(), user.getUsername());
    }
}
