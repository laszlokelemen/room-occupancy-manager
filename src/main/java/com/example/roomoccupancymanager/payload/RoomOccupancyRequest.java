package com.example.roomoccupancymanager.payload;

import validator.PositiveNumbers;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public record RoomOccupancyRequest(@PositiveNumbers List<BigDecimal> guests, int numberOfFreeEconomyRooms,
                                   int numberOfFreePremiumRooms) {
    /**
     * Retrieves the list of guests.
     *
     * @return the list of guests, or an empty list if there are no guests
     */
    public List<BigDecimal> getGuests() {
        return guests != null ? guests : Collections.emptyList();
    }
}