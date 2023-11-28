package com.example.demo.controller;

import com.example.demo.model.Restaurant;
import com.example.demo.request.RatingUpdateRequest;
import com.example.demo.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Void> addRestaurant(@RequestBody Restaurant restaurant) {
            restaurantService.addRestaurant(restaurant);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRestaurantRating(@PathVariable Long id, @RequestBody RatingUpdateRequest request) {
            restaurantService.updateRestaurantRating(id, request.getAverageRating(), request.getVotes());
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
            List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        // Add HATEOAS links to each restaurant
        for (Restaurant restaurant : restaurants) {
            restaurant.addSelfLink();
            restaurant.addUpdateLink();
            restaurant.addDeleteLink();
        }
            return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/query/{city}")//+param value
    public ResponseEntity<List<Restaurant>> getRestaurantsByCity(@PathVariable String city) {
            List<Restaurant> restaurants = restaurantService.getRestaurantsByCity(city);
            return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/query/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);

        // Add HATEOAS links
        restaurant.addSelfLink();
        restaurant.addUpdateLink();
        restaurant.addDeleteLink();

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
            restaurantService.deleteRestaurant(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/sort")
    public ResponseEntity<List<Restaurant>> sortRestaurantsByRating() {
        List<Restaurant> sortedRestaurants = restaurantService.sortRestaurantsByRating();
        return new ResponseEntity<>(sortedRestaurants, HttpStatus.OK);
    }
}