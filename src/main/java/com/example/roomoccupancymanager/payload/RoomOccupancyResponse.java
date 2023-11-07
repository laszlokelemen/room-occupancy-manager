package com.example.roomoccupancymanager.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RoomOccupancyResponse {
    @JsonProperty("usagePremium")
    private int numberOfOccupiedPremiumRooms;
    @JsonProperty("usageEconomy")
    private int numberOfOccupiedEconomyRooms;
    @JsonProperty("pricePremium")
    private BigDecimal priceOfPremiumRooms;
    @JsonProperty("priceEconomy")
    private BigDecimal priceOfEconomyRooms;
}