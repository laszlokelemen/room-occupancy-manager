package com.example.roomoccupancymanager.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
class RoomOccupancyControllerTest {
    private static final String JSON_CONTENT_TYPE = "application/json";
    private static final String URI = "/room/optimize";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testOptimize_WithEmptyRequest() throws Exception {
        mockMvc.perform(post(URI)
                        .contentType(JSON_CONTENT_TYPE)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testOptimize_WithValidRequest() throws Exception {
        String requestBody = "{" +
                "\"guests\":[23,45,155,374,22,99.99,100,101,115,209]," +
                "\"numberOfFreeEconomyRooms\":2," +
                "\"numberOfFreePremiumRooms\":7" +
                "}";

        mockMvc.perform(post(URI)
                        .contentType(JSON_CONTENT_TYPE)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    public void testOptimize_WithNegativeNumberInRequest() throws Exception {
        String requestBody = "{" +
                "\"guests\":[-23,45,155,374,22,99.99,100,101,115,209]," +
                "\"numberOfFreeEconomyRooms\":2," +
                "\"numberOfFreePremiumRooms\":7" +
                "}";

        mockMvc.perform(post(URI)
                        .contentType(JSON_CONTENT_TYPE)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}