package com.example.roomoccupancymanager.service;

import com.example.roomoccupancymanager.payload.RoomOccupancyRequest;
import com.example.roomoccupancymanager.payload.RoomOccupancyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RoomOccupancyServiceImplTest {

    private RoomOccupancyRequest occupancyRequest;
    private RoomOccupancyServiceImpl occupancyService;

    @BeforeEach
    public void setup() {
        occupancyRequest = new RoomOccupancyRequest();
        occupancyService = new RoomOccupancyServiceImpl();
        occupancyRequest.setGuests(Arrays.asList(23.0, 45.0, 155.0, 374.0, 22.0, 99.99, 100.0, 101.0, 115.0, 209.0));
    }

    @Test
    public void testOptimize_WithEquallyDistributedRooms() {
        occupancyRequest.setNumberOfFreeEconomyRooms(3);
        occupancyRequest.setNumberOfFreePremiumRooms(3);

        RoomOccupancyResponse response = occupancyService.optimize(occupancyRequest);
        assertEquals(3, response.getNumberOfOccupiedEconomyRooms());
        assertEquals(3, response.getNumberOfOccupiedPremiumRooms());
        assertEquals(738, response.getPriceOfPremiumRooms());
        assertEquals(167.99, response.getPriceOfEconomyRooms());
    }

    @Test
    public void testOptimize_WithMoreRoomsThanGuests() {
        occupancyRequest.setNumberOfFreePremiumRooms(7);
        occupancyRequest.setNumberOfFreeEconomyRooms(5);

        RoomOccupancyResponse response = occupancyService.optimize(occupancyRequest);
        assertEquals(6, response.getNumberOfOccupiedPremiumRooms());
        assertEquals(4, response.getNumberOfOccupiedEconomyRooms());
        assertEquals(1054, response.getPriceOfPremiumRooms());
        assertEquals(189.99, response.getPriceOfEconomyRooms());
    }

    @Test
    public void testOptimize_WithMoreEcoRoomsThanEcoGuests() {
        occupancyRequest.setNumberOfFreePremiumRooms(2);
        occupancyRequest.setNumberOfFreeEconomyRooms(7);

        RoomOccupancyResponse response = occupancyService.optimize(occupancyRequest);
        assertEquals(2, response.getNumberOfOccupiedPremiumRooms());
        assertEquals(4, response.getNumberOfOccupiedEconomyRooms());
        assertEquals(583, response.getPriceOfPremiumRooms());
        assertEquals(189.99, response.getPriceOfEconomyRooms());
    }

    @Test
    public void testOptimize_WithOneEcoGuestGoesToPremiumRoom() {
        occupancyRequest.setNumberOfFreePremiumRooms(7);
        occupancyRequest.setNumberOfFreeEconomyRooms(1);

        RoomOccupancyResponse response = occupancyService.optimize(occupancyRequest);
        assertEquals(7, response.getNumberOfOccupiedPremiumRooms());
        assertEquals(1, response.getNumberOfOccupiedEconomyRooms());
        assertEquals(1153.99, response.getPriceOfPremiumRooms());
        assertEquals(45, response.getPriceOfEconomyRooms());
    }

    @Test
    public void testOptimize_WithNoQuests() {
        occupancyRequest.setGuests(List.of());
        RoomOccupancyResponse response = occupancyService.optimize(occupancyRequest);
        assertEquals(0, response.getNumberOfOccupiedEconomyRooms());
        assertEquals(0, response.getNumberOfOccupiedPremiumRooms());
        assertEquals(0, response.getPriceOfPremiumRooms());
        assertEquals(0, response.getPriceOfEconomyRooms());
    }
}