package com.example.demo.controller;

import com.example.demo.exception.RestExceptionHandler;
import com.example.demo.exception.RestaurantNotFoundException;
import com.example.demo.model.Restaurant;
import com.example.demo.request.RatingUpdateRequest;
import com.example.demo.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    @BeforeAll
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
    }

    @Test
    void testAddRestaurant() throws Exception {
        // Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant.setCity("Test City");
        restaurant.setEstimatedCost(1000);
        restaurant.setAverageRating("4.5");
        restaurant.setVotes(100);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurant)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        verify(restaurantService, times(1)).addRestaurant(restaurant);
    }

    @Test
    void testUpdateRestaurantRating() throws Exception {
        // Arrange
        Long id = 1L;
        RatingUpdateRequest request = new RatingUpdateRequest();
        request.setAverageRating("4.8");
        request.setVotes(20000);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/restaurant/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(restaurantService, times(1)).updateRestaurantRating(id, request.getAverageRating(), request.getVotes());
    }

    @Test
    void testGetAllRestaurants() throws Exception {
        // Arrange
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(1L);
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(2L);

        when(restaurantService.getAllRestaurants()).thenReturn(Arrays.asList(restaurant1, restaurant2));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurant"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void testGetRestaurantsByCity() throws Exception {
        // Arrange
        String city = "TestCity";
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(1L);
        restaurant1.setCity(city);
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(2L);
        restaurant2.setCity(city);

        when(restaurantService.getRestaurantsByCity(city)).thenReturn(Arrays.asList(restaurant1, restaurant2));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurant/query?city={city}", city))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void testGetRestaurantById() throws Exception {
        // Arrange
        Long id = 1L;
        Restaurant restaurant = createRestaurant("Test Restaurant", "Test City", 1000, "4.5", 100);
        restaurant.setId(id);

        when(restaurantService.getRestaurantById(id)).thenReturn(restaurant);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurant/query?id={id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Restaurant"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Test City"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.estimatedCost").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.averageRating").value("4.5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.votes").value(100));
    }

    @Test
    void testGetRestaurantById_NotFound() throws Exception {
        // Arrange
        Long id = 1L;

        when(restaurantService.getRestaurantById(id)).thenThrow(new RestaurantNotFoundException(1L));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurant/query?id={id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteRestaurant() throws Exception {
        // Arrange
        Long id = 1L;

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/restaurant/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(restaurantService, times(1)).deleteRestaurant(id);
    }

    private Restaurant createRestaurant(String name, String city, int estimatedCost, String averageRating, int votes) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setCity(city);
        restaurant.setEstimatedCost(estimatedCost);
        restaurant.setAverageRating(averageRating);
        restaurant.setVotes(votes);
        return restaurant;
    }
}
