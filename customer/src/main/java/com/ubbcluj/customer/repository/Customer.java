package com.ubbcluj.customer.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity<Long>{
    @Column(nullable = false)
    protected String name;
    @Column(nullable = false)
    protected Integer rating;
    @Column(nullable = false)
    protected Long associatedUserId;
}
