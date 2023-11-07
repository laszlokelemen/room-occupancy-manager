package com.example.roomoccupancymanager.service;

import com.example.roomoccupancymanager.payload.RoomOccupancyRequest;
import com.example.roomoccupancymanager.payload.RoomOccupancyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RoomOccupancyServiceImplTest {

    private RoomOccupancyRequest occupancyRequest;
    private RoomOccupancyServiceImpl occupancyService;

    @BeforeEach
    void setup() {
        occupancyRequest = new RoomOccupancyRequest();
        occupancyService = new RoomOccupancyServiceImpl();

        List<BigDecimal> guests = Arrays.asList(
                BigDecimal.valueOf(23),
                BigDecimal.valueOf(45),
                BigDecimal.valueOf(155),
                BigDecimal.valueOf(374),
                BigDecimal.valueOf(22),
                BigDecimal.valueOf(99.99),
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(101),
                BigDecimal.valueOf(115),
                BigDecimal.valueOf(209)
        );

        occupancyRequest.setGuests(guests);
    }

    @Test
    void testOptimize_WithEquallyDistributedRooms() {
        occupancyRequest.setNumberOfFreeEconomyRooms(3);
        occupancyRequest.setNumberOfFreePremiumRooms(3);

        RoomOccupancyResponse response = occupancyService.optimize(occupancyRequest);
        assertEquals(3, response.getNumberOfOccupiedEconomyRooms());
        assertEquals(3, response.getNumberOfOccupiedPremiumRooms());
        assertEquals(BigDecimal.valueOf(738), response.getPriceOfPremiumRooms());
        assertEquals(BigDecimal.valueOf(167.99), response.getPriceOfEconomyRooms());
    }

    @Test
    void testOptimize_WithMoreRoomsThanGuests() {
        occupancyRequest.setNumberOfFreePremiumRooms(7);
        occupancyRequest.setNumberOfFreeEconomyRooms(5);

        RoomOccupancyResponse response = occupancyService.optimize(occupancyRequest);
        assertEquals(6, response.getNumberOfOccupiedPremiumRooms());
        assertEquals(4, response.getNumberOfOccupiedEconomyRooms());
        assertEquals(BigDecimal.valueOf(1054), response.getPriceOfPremiumRooms());
        assertEquals(BigDecimal.valueOf(189.99), response.getPriceOfEconomyRooms());
    }

    @Test
    void testOptimize_WithMoreEcoRoomsThanEcoGuests() {
        occupancyRequest.setNumberOfFreePremiumRooms(2);
        occupancyRequest.setNumberOfFreeEconomyRooms(7);

        RoomOccupancyResponse response = occupancyService.optimize(occupancyRequest);
        assertEquals(2, response.getNumberOfOccupiedPremiumRooms());
        assertEquals(4, response.getNumberOfOccupiedEconomyRooms());
        assertEquals(BigDecimal.valueOf(583), response.getPriceOfPremiumRooms());
        assertEquals(BigDecimal.valueOf(189.99), response.getPriceOfEconomyRooms());
    }

    @Test
    void testOptimize_WithOneEcoGuestGoesToPremiumRoom() {
        occupancyRequest.setNumberOfFreePremiumRooms(7);
        occupancyRequest.setNumberOfFreeEconomyRooms(1);

        RoomOccupancyResponse response = occupancyService.optimize(occupancyRequest);
        assertEquals(7, response.getNumberOfOccupiedPremiumRooms());
        assertEquals(1, response.getNumberOfOccupiedEconomyRooms());
        assertEquals(BigDecimal.valueOf(1153.99), response.getPriceOfPremiumRooms());
        assertEquals(BigDecimal.valueOf(45), response.getPriceOfEconomyRooms());
    }

    @Test
    void testOptimize_WithNoQuests() {
        occupancyRequest.setGuests(List.of());
        RoomOccupancyResponse response = occupancyService.optimize(occupancyRequest);
        assertEquals(0, response.getNumberOfOccupiedEconomyRooms());
        assertEquals(0, response.getNumberOfOccupiedPremiumRooms());
        assertEquals(BigDecimal.valueOf(0), response.getPriceOfPremiumRooms());
        assertEquals(BigDecimal.valueOf(0), response.getPriceOfEconomyRooms());
    }
}