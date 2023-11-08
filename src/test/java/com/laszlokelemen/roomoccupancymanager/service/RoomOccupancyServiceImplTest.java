package com.laszlokelemen.roomoccupancymanager.service;

import com.laszlokelemen.roomoccupancymanager.payload.RoomOccupancyRequest;
import com.laszlokelemen.roomoccupancymanager.payload.RoomOccupancyResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RoomOccupancyServiceImplTest {

    private RoomOccupancyRequest occupancyRequest;

    @Spy
    private RoomOccupancyServiceImpl occupancyService;


    @Test
    void testOptimize_WithEquallyDistributedRooms() {
        occupancyRequest = new RoomOccupancyRequest(getGuests(), 3, 3);

        RoomOccupancyResponse response = occupancyService.optimize(occupancyRequest);
        assertEquals(3, response.numberOfOccupiedPremiumRooms());
        assertEquals(3, response.numberOfOccupiedPremiumRooms());
        assertEquals(BigDecimal.valueOf(738), response.priceOfPremiumRooms());
        assertEquals(BigDecimal.valueOf(167.99), response.priceOfEconomyRooms());
    }

    @Test
    void testOptimize_WithMoreRoomsThanGuests() {
        occupancyRequest = new RoomOccupancyRequest(getGuests(), 5, 7);

        RoomOccupancyResponse response = occupancyService.optimize(occupancyRequest);
        assertEquals(6, response.numberOfOccupiedPremiumRooms());
        assertEquals(4, response.numberOfOccupiedEconomyRooms());
        assertEquals(BigDecimal.valueOf(1054), response.priceOfPremiumRooms());
        assertEquals(BigDecimal.valueOf(189.99), response.priceOfEconomyRooms());
    }

    @Test
    void testOptimize_WithMoreEcoRoomsThanEcoGuests() {
        occupancyRequest = new RoomOccupancyRequest(getGuests(), 7, 2);

        RoomOccupancyResponse response = occupancyService.optimize(occupancyRequest);
        assertEquals(2, response.numberOfOccupiedPremiumRooms());
        assertEquals(4, response.numberOfOccupiedEconomyRooms());
        assertEquals(BigDecimal.valueOf(583), response.priceOfPremiumRooms());
        assertEquals(BigDecimal.valueOf(189.99), response.priceOfEconomyRooms());
    }

    @Test
    void testOptimize_WithOneEcoGuestGoesToPremiumRoom() {
        occupancyRequest = new RoomOccupancyRequest(getGuests(), 1, 7);

        RoomOccupancyResponse response = occupancyService.optimize(occupancyRequest);
        assertEquals(7, response.numberOfOccupiedPremiumRooms());
        assertEquals(1, response.numberOfOccupiedEconomyRooms());
        assertEquals(BigDecimal.valueOf(1153.99), response.priceOfPremiumRooms());
        assertEquals(BigDecimal.valueOf(45), response.priceOfEconomyRooms());
    }

    @Test
    void testOptimize_WithNoQuests() {
        occupancyRequest = new RoomOccupancyRequest(getGuests(), 0, 0);

        RoomOccupancyResponse response = occupancyService.optimize(occupancyRequest);
        assertEquals(0, response.numberOfOccupiedEconomyRooms());
        assertEquals(0, response.numberOfOccupiedPremiumRooms());
        assertEquals(BigDecimal.valueOf(0), response.priceOfPremiumRooms());
        assertEquals(BigDecimal.valueOf(0), response.priceOfEconomyRooms());
    }

    private static List<BigDecimal> getGuests() {
        return Arrays.asList(
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
    }
}