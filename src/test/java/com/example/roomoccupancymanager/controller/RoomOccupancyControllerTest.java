package com.example.roomoccupancymanager.controller;

import com.example.roomoccupancymanager.payload.RoomOccupancyRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

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

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testOptimize_WithEmptyRequest() throws Exception {
        RoomOccupancyRequest request = new RoomOccupancyRequest();
        mockMvc.perform(post(URI)
                        .contentType(JSON_CONTENT_TYPE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void testOptimize_WithValidRequest() throws Exception {
        RoomOccupancyRequest request = new RoomOccupancyRequest();
        request.setGuests(Arrays.asList(23.0, 45.0, 155.0, 374.0, 22.0, 99.99, 100.0, 101.0, 115.0, 209.0));
        request.setNumberOfFreePremiumRooms(7);
        request.setNumberOfFreeEconomyRooms(2);

        mockMvc.perform(post(URI)
                        .contentType(JSON_CONTENT_TYPE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}