package com.ubbcluj.customer.repository;


import java.util.Optional;

public interface CustomerRepository extends BaseRepository<Customer, Long>{
    Optional<Customer> findByAssociatedUserId(Long id);
}
