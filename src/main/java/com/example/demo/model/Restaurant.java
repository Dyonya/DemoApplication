package com.example.demo.model;

import com.example.demo.controller.RestaurantController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Entity
public class Restaurant extends RepresentationModel<Restaurant>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String name;
    private int estimatedCost;
    private String averageRating;
    private int votes;

    public Restaurant() {
    }

    public Restaurant(Long id, String city, String name, int estimatedCost, String averageRating, int votes) {
        this.id = id;
        this.city = city;
        this.name = name;
        this.estimatedCost = estimatedCost;
        this.averageRating = averageRating;
        this.votes = votes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(int estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", name='" + name + '\'' +
                ", estimatedCost=" + estimatedCost +
                ", averageRating='" + averageRating + '\'' +
                ", votes=" + votes +
                '}';
    }

    // Add HATEOAS links
    public void addSelfLink() {
        add(linkTo(methodOn(RestaurantController.class).getRestaurantById(id)).withSelfRel());
    }

    public void addUpdateLink() {
        add(linkTo(methodOn(RestaurantController.class).updateRestaurantRating(id, null)).withRel("update"));
    }

    public void addDeleteLink() {
        add(linkTo(methodOn(RestaurantController.class).deleteRestaurant(id)).withRel("delete"));
    }
}

