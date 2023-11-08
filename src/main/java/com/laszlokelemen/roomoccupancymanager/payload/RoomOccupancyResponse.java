package com.laszlokelemen.roomoccupancymanager.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;


public record RoomOccupancyResponse(
        @JsonProperty("usagePremium") int numberOfOccupiedPremiumRooms,
        @JsonProperty("usageEconomy") int numberOfOccupiedEconomyRooms,
        @JsonProperty("pricePremium") BigDecimal priceOfPremiumRooms,
        @JsonProperty("priceEconomy") BigDecimal priceOfEconomyRooms
) {
}