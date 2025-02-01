package com.ubbcluj.authentication.repository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long>{
    boolean existsByUsername(String username);
    boolean existsByUsernameAndPassword(String username, String password);
    Optional<User> findByUsernameAndPassword(String username, String password);
}
