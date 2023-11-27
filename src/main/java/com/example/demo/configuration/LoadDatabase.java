package com.example.demo.configuration;

import com.example.demo.model.Restaurant;
import com.example.demo.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(RestaurantRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Restaurant(1L,"Miami", "Byg Cheeseburger", 1600, "4.9", 16203)));
            log.info("Preloading " + repository.save(new Restaurant(2L,"New York", "Food Palace", 1800, "4.5", 12000)));
            log.info("Preloading " + repository.save(new Restaurant(3L,"Los Angeles", "Tasty Bites", 2000, "4.8", 15000)));
        };
    }
}
