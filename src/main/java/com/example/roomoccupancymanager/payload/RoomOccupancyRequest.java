package com.example.roomoccupancymanager.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomOccupancyRequest {
    private List<BigDecimal> guests;
    private int numberOfFreeEconomyRooms;
    private int numberOfFreePremiumRooms;

    public List<BigDecimal> getGuests() {
        return guests != null ? guests : Collections.emptyList();
    }
}