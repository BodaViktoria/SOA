package com.ubbcluj.authentication.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserEntity extends BaseEntity<Long> {
    @Column(nullable = false, unique = true)
    protected String username;
    @Column(nullable = false)
    protected String password;
}
