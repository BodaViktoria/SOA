package com.ubbcluj.restaurant.service;

import com.ubbcluj.restaurant.dtos.*;
import com.ubbcluj.restaurant.repository.Item;
import com.ubbcluj.restaurant.repository.ItemRepository;
import com.ubbcluj.restaurant.repository.Restaurant;
import com.ubbcluj.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    protected final RestaurantRepository restaurantRepository;
    protected final ItemRepository itemRepository;
    private final RestTemplate restTemplate;

    @Value("${authenticationServiceURL}")
    private String authUrl;

    @Value("${authenticationServicePort}")
    private String authPort;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, ItemRepository itemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.itemRepository = itemRepository;
        restTemplate = new RestTemplate();
    }

    @Override
    public RestaurantDto register(RestaurantCreateDto dto) {
        // Set headers for content type (JSON)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RegisterDto requestBody = new RegisterDto(dto.getUsername(), dto.getPassword());

        // Wrap request body and headers in HttpEntity
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

        // Send POST request
        ResponseEntity<UserDto> response = restTemplate.exchange(getAuthBase() + "/api/user/register", HttpMethod.POST, entity, UserDto.class);

        // Return the response body as a String
        UserDto user = response.getBody();
        var restaurant = restaurantRepository.save(new Restaurant(dto.getName(), dto.getDescription(), user.getId()));
        return new RestaurantDto(restaurant.getId(), restaurant.getAssociatedUserId(), restaurant.getName(), restaurant.getDescription());
    }

    @Override
    public ItemDto createItem(ItemCreateDto dto, Long requester) {
        var restaurant = restaurantRepository.findByAssociatedUserId(requester);
        if (restaurant.isEmpty()){
            throw new RuntimeException("Restaurant does not exist!");
        }
        Item item = new Item(dto.getName(), dto.getDescription(), dto.getPrice(), restaurant.get());
        var savedItem = itemRepository.save(item);
        return new ItemDto(savedItem.getId(), savedItem.getName(), savedItem.getDescription(), savedItem.getPrice());
    }
    @Override
    public List<RestaurantDto> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(r -> new RestaurantDto(r.getId(), r.getAssociatedUserId(), r.getName(), r.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getItemsForRestaurant(Long restaurantId) {
        return itemRepository.findByRestaurantId(restaurantId).stream()
                .map(item -> new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(Long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isPresent()) {
            itemRepository.delete(item.get());
        } else {
            throw new RuntimeException("Item not found!");
        }
    }

    @Override
    public ItemDto updateItem(Long itemId, ItemCreateDto dto) {
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        if (itemOptional.isEmpty()) {
            throw new RuntimeException("Item not found!");
        }
        Item item = itemOptional.get();
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        var updatedItem = itemRepository.save(item);
        return new ItemDto(updatedItem.getId(), updatedItem.getName(), updatedItem.getDescription(), updatedItem.getPrice());
    }

    @Override
    public List<ItemDto> getAllItemsByIds(ItemIdsDto dto) {
        List<Item> items = itemRepository.findByIdIn(dto.getItemIds());
        List<ItemDto> itemDtos = new ArrayList<>();
        for(Item item : items){
            itemDtos.add(new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getPrice()));
        }
        return itemDtos;
    }

    private String getAuthBase(){
        return "http://" + authUrl + ":" + authPort;
    }
}
