package com.example.demo.repository;

import com.example.demo.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant r WHERE r.city = :city")
    List<Restaurant> getRestaurantsByCity(@Param("city") String city);
    @Query("SELECT r FROM Restaurant r ORDER BY r.averageRating DESC")
    List<Restaurant> findAllByOrderByAverageRatingDesc();

}
