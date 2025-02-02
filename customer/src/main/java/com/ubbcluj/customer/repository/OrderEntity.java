package com.ubbcluj.customer.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity extends BaseEntity<Long>{
    @Column(nullable = false)
    protected String itemIds;

    @ManyToOne
    protected Customer customer;
}
