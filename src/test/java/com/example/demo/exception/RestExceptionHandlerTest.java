package com.example.demo.exception;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = RestExceptionHandler.class)
public class RestExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void handleRestaurantNotFoundException() throws Exception {
        Long nonExistentId = 123L;

        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Could not find restaurant " + nonExistentId));
    }

    @Test
    void handleGenericException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/error")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string("An internal server error occurred."));
    }
}

