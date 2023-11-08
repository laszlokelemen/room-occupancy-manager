package com.laszlokelemen.roomoccupancymanager.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

    /**
     * Test the optimize function with a valid request.
     *
     * @param requestBody the JSON request body
     * @throws Exception if an error occurs during the test
     */
    @ParameterizedTest
    @ValueSource(strings = {"{}", "{" +
            "\"guests\":[23,45,155,374,22,99.99,100,101,115,209]," +
            "\"numberOfFreeEconomyRooms\":2," +
            "\"numberOfFreePremiumRooms\":7" +
            "}"})
    public void testOptimize_WithValidRequest(String requestBody) throws Exception {
        mockMvc.perform(post(URI)
                        .contentType(JSON_CONTENT_TYPE)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    /**
     * Test case for the optimize function when a negative number is present in the request.
     *
     * @throws Exception if an exception occurs during the test
     */
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