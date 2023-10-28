package com.example.roomoccupancymanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomOccupancyResponse {
    @JsonProperty("usagePremium")
    private int numberOfOccupiedPremiumRooms;
    @JsonProperty("usageEconomy")
    private int numberOfOccupiedEconomyRooms;
    @JsonProperty("pricePremium")
    private double priceOfPremiumRooms;
    @JsonProperty("priceEconomy")
    private double priceOfEconomyRooms;
}