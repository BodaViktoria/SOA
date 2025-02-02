package com.ubbcluj.restaurant.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity extends BaseEntity<Long> {
    @Column(nullable = false)
    protected Long orderId;
    @ManyToOne
    protected Restaurant restaurant;
    @Column(nullable = false)
    protected Long customerId;
    @ManyToMany
    protected List<Item> items;
    @Column(nullable = false)
    protected Long finalPrice;
}
