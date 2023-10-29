package com.example.roomoccupancymanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomOccupancyRequest {
    private List<Double> guests;
    private int numberOfFreeEconomyRooms;
    private int numberOfFreePremiumRooms;
}