package com.example.demo.service;

import com.example.demo.model.Restaurant;
import org.springframework.context.annotation.Bean;

import java.util.List;
public interface RestaurantService {

    List<Restaurant> getAllRestaurants();

    List<Restaurant> getRestaurantsByCity(String city);

    Restaurant getRestaurantById(Long id);

    void addRestaurant(Restaurant restaurant);

    void updateRestaurantRating(Long id, String averageRating, int votes);

    void deleteRestaurant(Long id);

    List<Restaurant> sortRestaurantsByRating();

}
