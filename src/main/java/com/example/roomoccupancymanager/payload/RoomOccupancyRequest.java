package com.example.roomoccupancymanager.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomOccupancyRequest {
    private List<Double> guests;
    private int numberOfFreeEconomyRooms;
    private int numberOfFreePremiumRooms;

    public List<Double> getGuests() {
        return guests != null ? guests : Collections.emptyList();
    }
}