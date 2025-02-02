package com.ubbcluj.customer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubbcluj.customer.dto.*;
import com.ubbcluj.customer.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService{

    protected final CustomerRepository customerRepository;

    protected final OrderRepository orderRepository;

    private final RestTemplate restTemplate;

    protected final JmsTemplate jmsTemplate;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private String orderQueue = "queue/orders";

    @Value("${authenticationServiceURL}")
    private String authUrl;

    @Value("${authenticationServicePort}")
    private String authPort;

    @Value("${restaurantServiceURL}")
    private String restaurantUrl;

    @Value("${restaurantServicePort}")
    private String restaurantPort;

    @Value("${lambdaURL}")
    private String lambdaUrl;

    private String kafkaTopic = "transaction";
    private final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    public CustomerServiceImpl(CustomerRepository customerRepository, OrderRepository orderRepository, JmsTemplate jmsTemplate, KafkaTemplate<String, String> kafkaTemplate) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.jmsTemplate = jmsTemplate;
        this.kafkaTemplate = kafkaTemplate;
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
    public OrderDto order(CreateOrderDto dto, Long requester) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestBody = new ItemIdsDto(dto.getItemIds());

        // Wrap request body and headers in HttpEntity
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

        // Send POST request
        ResponseEntity<List<ItemDto>> response = restTemplate.exchange(getRestaurantBase() + "/restaurant/api/item/itemList", HttpMethod.POST, entity, new ParameterizedTypeReference<List<ItemDto>>() {});

        // Return the response body as a String
        List<ItemDto> itemDtos = response.getBody();
        if(itemDtos.isEmpty()){
            throw new RuntimeException("No items!");
        }
        var customer = customerRepository.findByAssociatedUserId(requester);
        logger.info("----------------------------->>>>>Requester: " + requester);

        if(customer.isEmpty()){
            throw new RuntimeException("Customer does not exist!");
        }
        Integer customerRating = customer.get().getRating();

        DiscountRequestDto discountRequestDto = new DiscountRequestDto(itemDtos.stream().map(i->i.getPrice()).toList(), customerRating);

        // Wrap request body and headers in HttpEntity
        HttpEntity<Object> entityLambda = new HttpEntity<>(discountRequestDto, headers);

        // Send POST request
        ResponseEntity<DiscountResponseDto> responseLambda = restTemplate.exchange(lambdaUrl, HttpMethod.POST, entityLambda, DiscountResponseDto.class);

        // Return the response body as a String
        DiscountResponseDto discountResponse = responseLambda.getBody();
        OrderEntity finalOrderDto = new OrderEntity(itemDtos.stream().map(i->i.getId().toString()).collect(Collectors.joining(",")), customer.get(), discountResponse.getFinal_price());
        var savedOrder = orderRepository.save(finalOrderDto);


        OrderRequestJMSDto orderRequestJMSDto = new OrderRequestJMSDto(savedOrder.getId(), itemDtos.get(0).getRestaurantId(),
                requester, itemDtos.stream().map(i->i.getId()).toList(), discountResponse.getFinal_price());

        jmsTemplate.convertAndSend(orderQueue, orderRequestJMSDto);

        // kafka
        TransactionDto transactionDto = new TransactionDto(customer.get().getAssociatedUserId(), discountResponse.getFinal_price());

        ObjectMapper objectMapper = new ObjectMapper();

        try{
            String json = objectMapper.writeValueAsString(transactionDto);
            kafkaTemplate.send(kafkaTopic, json);
        }
        catch (JsonProcessingException e){
            logger.error(e.getMessage());
        }

        return new OrderDto(savedOrder.getId());
    }

    public int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(5) + 1;
    }

    private String getAuthBase(){
        return "http://" + authUrl + ":" + authPort;
    }

    private String getRestaurantBase(){
        return "http://" + restaurantUrl + ":" + restaurantPort;
    }
}
