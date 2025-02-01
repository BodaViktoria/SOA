package com.ubbcluj.authentication.repository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<UserEntity, Long>{
    boolean existsByUsername(String username);
    boolean existsByUsernameAndPassword(String username, String password);
    Optional<UserEntity> findByUsernameAndPassword(String username, String password);
}
