package com.ubbcluj.customer.service;

import com.ubbcluj.customer.dto.CreateOrderDto;
import com.ubbcluj.customer.dto.CustomerCreateDto;
import com.ubbcluj.customer.dto.CustomerDto;
import com.ubbcluj.customer.dto.OrderDto;
import com.ubbcluj.customer.repository.Customer;
import com.ubbcluj.customer.repository.CustomerRepository;
import com.ubbcluj.customer.repository.RegisterDto;
import com.ubbcluj.customer.repository.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
public class CustomerServiceImpl implements CustomerService{

    protected final CustomerRepository customerRepository;

    private final RestTemplate restTemplate;

    @Value("${authenticationServiceURL}")
    private String authUrl;

    @Value("${authenticationServicePort}")
    private String authPort;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        restTemplate = new RestTemplate();
    }

    @Override
    public CustomerDto register(CustomerCreateDto dto) {
        Integer rating = getRandomNumber();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RegisterDto requestBody = new RegisterDto(dto.getUsername(), dto.getPassword());

        // Wrap request body and headers in HttpEntity
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

        // Send POST request
        ResponseEntity<UserDto> response = restTemplate.exchange(getAuthBase() + "/api/user/register", HttpMethod.POST, entity, UserDto.class);

        // Return the response body as a String
        UserDto user = response.getBody();

        var customer = customerRepository.save(new Customer(dto.getName(), rating, user.getId()));
        return new CustomerDto(customer.getId(), customer.getName(), customer.getRating());
    }

    @Override
    public OrderDto order(CreateOrderDto dto) {
        return null;
    }

    public int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(5) + 1;
    }

    private String getAuthBase(){
        return "http://" + authUrl + ":" + authPort;
    }
}
