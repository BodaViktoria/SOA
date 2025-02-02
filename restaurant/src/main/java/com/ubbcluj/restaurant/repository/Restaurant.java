package com.ubbcluj.restaurant.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant extends BaseEntity<Long> {
    @Column(nullable = false)
    protected String name;
    @Column
    protected String description;
    @Column(nullable = false)
    protected Long associatedUserId;
}
