package com.ubbcluj.restaurant.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @OneToMany
    protected List<Item> itemIds;
    @Column(nullable = false)
    protected Long finalPrice;
}
