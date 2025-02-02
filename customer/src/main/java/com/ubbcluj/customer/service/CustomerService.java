package com.ubbcluj.customer.service;

import com.ubbcluj.customer.dto.CreateOrderDto;
import com.ubbcluj.customer.dto.CustomerCreateDto;
import com.ubbcluj.customer.dto.CustomerDto;
import com.ubbcluj.customer.dto.OrderDto;

public interface CustomerService {
    CustomerDto register(CustomerCreateDto dto);
    OrderDto order(CreateOrderDto dto, Long requester);
}
