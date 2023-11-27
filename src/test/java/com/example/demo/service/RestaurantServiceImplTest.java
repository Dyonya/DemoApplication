package com.example.demo.service;

import com.example.demo.model.Restaurant;
import com.example.demo.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @Test
    void testGetAllRestaurants() {
        // Arrange
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(1L);
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(2L);

        when(restaurantRepository.findAll()).thenReturn(Arrays.asList(restaurant1, restaurant2));

        // Act
        List<Restaurant> result = restaurantService.getAllRestaurants();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testGetRestaurantsByCity() {
        // Arrange
        String city = "Miami";
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setCity(city);
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setCity(city);

        when(restaurantRepository.findByCity(city)).thenReturn(Arrays.asList(restaurant1, restaurant2));

        // Act
        List<Restaurant> result = restaurantService.getRestaurantsByCity(city);

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testGetRestaurantById() {
        // Arrange
        Long id = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);

        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurant));

        // Act
        Restaurant result = restaurantService.getRestaurantById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void testAddRestaurant() {
        // Arrange
        Restaurant restaurant = new Restaurant();

        // Act
        restaurantService.addRestaurant(restaurant);

        // Assert
        verify(restaurantRepository, times(1)).save(restaurant);
    }

    @Test
    void testUpdateRestaurantRating() {
        // Arrange
        Long id = 1L;
        String newAverageRating = "4.8";
        int newVotes = 20000;

        Restaurant existingRestaurant = new Restaurant();
        existingRestaurant.setId(id);

        when(restaurantRepository.findById(id)).thenReturn(Optional.of(existingRestaurant));

        // Act
        restaurantService.updateRestaurantRating(id, newAverageRating, newVotes);

        // Assert
        assertEquals(newAverageRating, existingRestaurant.getAverageRating());
        assertEquals(newVotes, existingRestaurant.getVotes());
        verify(restaurantRepository, times(1)).save(existingRestaurant);
    }

    @Test
    void testDeleteRestaurant() {
        // Arrange
        Long id = 1L;

        // Act
        restaurantService.deleteRestaurant(id);

        // Assert
        verify(restaurantRepository, times(1)).deleteById(id);
    }
}
