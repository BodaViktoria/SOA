package com.ubbcluj.restaurant.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubbcluj.restaurant.dtos.OrderRequestJMSDto;
import com.ubbcluj.restaurant.repository.*;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderListener {

    private final Logger logger = LoggerFactory.getLogger(OrderListener.class);
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;

    private final ItemRepository itemRepository;

    public OrderListener(OrderRepository orderRepository, RestaurantRepository restaurantRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.itemRepository = itemRepository;
    }

    @JmsListener(destination = "queue/orders")
    public void receiveAndForwardMessageFromQueue(Message message) throws JMSException {
        if (message instanceof TextMessage) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                String jsonMessage = ((TextMessage) message).getText();
                OrderRequestJMSDto order = mapper.readValue(jsonMessage, OrderRequestJMSDto.class);
                logger.info("---------------------->>>>>>>>  received order!");

                Optional<Restaurant> restaurant = restaurantRepository.findByAssociatedUserId(order.getRestaurantId());
                if(restaurant.isEmpty()){
                    throw new RuntimeException("Restaurant not found!");
                }

                List<Item> listOfItems = itemRepository.findByIdIn(order.getItemIds());

                OrderEntity orderEntity = new OrderEntity(order.getOrderId(), restaurant.get(), order.getCustomerId(), listOfItems, order.getFinalPrice());
                var savedOrder = orderRepository.save(orderEntity);

            } catch (Exception e) {
                throw new RuntimeException("Error processing message", e);
            }
        } else {
            throw new RuntimeException("Unsupported message type: " + message.getJMSType());
        }
    }
}
