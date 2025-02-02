package com.ubbcluj.transaction.repository;

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
public class TransactionEntity extends BaseEntity<Long>{
    @Column(nullable = false)
    protected Long customerId;
    @Column(nullable = false)
    protected Long amount;
}
