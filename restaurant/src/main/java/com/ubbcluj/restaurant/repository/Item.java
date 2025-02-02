package com.ubbcluj.restaurant.repository;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Item extends BaseEntity<Long>{
    @Column(nullable = false)
    @NonNull
    protected String name;
    @Column
    @NonNull
    protected String description;
    @Column(nullable = false)
    @NonNull
    protected Long price;
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    protected Restaurant restaurant;
}
