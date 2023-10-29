package com.example.roomoccupancymanager.service;

import com.example.roomoccupancymanager.payload.RoomOccupancyRequest;
import com.example.roomoccupancymanager.payload.RoomOccupancyResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class RoomOccupancyServiceImpl implements RoomOccupancyService {
    private static final int MAX_ECONOMY_PAYMENT = 100;

    /**
     * Optimizes the room occupancy based on the given request.
     *
     * @param request the room occupancy request
     * @return the optimized room occupancy response
     */
    @Override
    public RoomOccupancyResponse optimize(RoomOccupancyRequest request) {
        List<Double> guests = new ArrayList<>(request.getGuests());
        guests.sort(Comparator.reverseOrder());

        List<Double> premiumGuests = guests
                .stream()
                .filter(guest -> guest >= MAX_ECONOMY_PAYMENT)
                .toList();
        List<Double> premiumRoomGuests = distributePremiumGuests(request.getNumberOfFreePremiumRooms(), premiumGuests);

        List<Double> economyGuests = guests
                .stream()
                .filter(guest -> guest < MAX_ECONOMY_PAYMENT)
                .toList();

        List<Double> economyRoomGuests = distributeRestOfTheGuests(request, premiumRoomGuests, economyGuests);

        return RoomOccupancyResponse.builder()
                .numberOfOccupiedPremiumRooms(premiumRoomGuests.size())
                .numberOfOccupiedEconomyRooms(economyRoomGuests.size())
                .priceOfPremiumRooms(premiumRoomGuests.stream().mapToDouble(Double::doubleValue).sum())
                .priceOfEconomyRooms(economyRoomGuests.stream().mapToDouble(Double::doubleValue).sum())
                .build();
    }

    /**
     * Distributes premium guests to premium rooms based on the number of free premium rooms available.
     *
     * @param numberOfFreePremiumRooms the number of free premium rooms available
     * @param premiumGuests            the list of premium guests
     * @return the list of premium room guests
     */
    private List<Double> distributePremiumGuests(int numberOfFreePremiumRooms, List<Double> premiumGuests) {
        List<Double> premiumRoomGuests = new ArrayList<>();
        for (Double guest : premiumGuests) {
            if (premiumRoomGuests.size() < numberOfFreePremiumRooms) {
                premiumRoomGuests.add(guest);
            }
        }
        return premiumRoomGuests;
    }

    /**
     * Distributes the rest of the guests to the economy rooms based on the number of premium room guests and the number of free premium and economy rooms.
     *
     * @param request           the room occupancy request containing the number of free premium and economy rooms
     * @param premiumRoomGuests the list of premium room offers
     * @param economyGuests     the list of economy guests
     * @return the list of economy room guests after distributing the rest of the guests
     */
    private List<Double> distributeRestOfTheGuests(RoomOccupancyRequest request, List<Double> premiumRoomGuests, List<Double> economyGuests) {
        int numberOfFreeEconomyRooms = request.getNumberOfFreeEconomyRooms();
        int economyLeftOverRooms = economyGuests.size() - numberOfFreeEconomyRooms;
        List<Double> economyRoomGuests = new ArrayList<>();

        for (Double guest : economyGuests) {
            if (economyLeftOverRooms > 0 && premiumRoomGuests.size() < request.getNumberOfFreePremiumRooms()) {
                premiumRoomGuests.add(guest);
                economyLeftOverRooms--;
            } else if (economyRoomGuests.size() < numberOfFreeEconomyRooms) {
                economyRoomGuests.add(guest);
            }
        }
        return economyRoomGuests;
    }
}
