package com.ubbcluj.customer.controller;

import com.ubbcluj.customer.dto.CreateOrderDto;
import com.ubbcluj.customer.dto.CustomerCreateDto;
import com.ubbcluj.customer.dto.CustomerDto;
import com.ubbcluj.customer.dto.OrderDto;
import com.ubbcluj.customer.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CustomerController {
    protected final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    ResponseEntity<CustomerDto> register(@RequestBody CustomerCreateDto dto) {
        return ResponseEntity.ok().body(customerService.register(dto));
    }

    @PostMapping("/order")
    ResponseEntity<OrderDto> order(@RequestBody CreateOrderDto dto, @RequestHeader("userId") Long requester){
        return ResponseEntity.ok().body(customerService.order(dto, requester));
    }
}
