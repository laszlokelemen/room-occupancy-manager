package com.laszlokelemen.roomoccupancymanager.service;

import com.laszlokelemen.roomoccupancymanager.payloads.RoomOccupancyRequest;
import com.laszlokelemen.roomoccupancymanager.payloads.RoomOccupancyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class RoomOccupancyServiceImpl implements RoomOccupancyService {
    private static final BigDecimal MAX_ECONOMY_PAYMENT = BigDecimal.valueOf(100);

    /**
     * Optimizes the room occupancy based on the given request.
     *
     * @param request the room occupancy request
     * @return the optimized room occupancy response
     */
    @Override
    public RoomOccupancyResponse optimize(RoomOccupancyRequest request) {
        var guests = new ArrayList<>(request.getGuests());
        guests.sort(Comparator.reverseOrder());

        var premiumGuests = guests.stream()
                .filter(guest -> guest.compareTo(MAX_ECONOMY_PAYMENT) >= 0)
                .toList();
        var premiumRoomGuests = distributePremiumGuests(request.numberOfFreePremiumRooms(),
                premiumGuests);

        var economyGuests = guests.stream()
                .filter(guest -> guest.compareTo(MAX_ECONOMY_PAYMENT) < 0)
                .toList();
        var economyRoomGuests = distributeRestOfTheGuests(request, premiumRoomGuests, economyGuests);

        var totalPremiumPayment = premiumRoomGuests.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var totalEconomyPayment = economyRoomGuests.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new RoomOccupancyResponse(premiumRoomGuests.size(), economyRoomGuests.size(),
                totalPremiumPayment, totalEconomyPayment);
    }

    /**
     * Distributes premium guests to premium rooms based on the number of free premium rooms available.
     *
     * @param numberOfFreePremiumRooms the number of free premium rooms available
     * @param premiumGuests            the list of premium guests
     * @return the list of premium room guests
     */
    private List<BigDecimal> distributePremiumGuests(int numberOfFreePremiumRooms, List<BigDecimal> premiumGuests) {
        List<BigDecimal> premiumRoomGuests = new ArrayList<>();
        premiumGuests.stream()
                .filter(guest -> premiumRoomGuests.size() < numberOfFreePremiumRooms)
                .forEach(premiumRoomGuests::add);
        return premiumRoomGuests;
    }

    /**
     * Distributes the rest of the guests to the economy rooms based on the number of premium room guests and the number
     * of free premium and economy rooms.
     *
     * @param request           the room occupancy request containing the number of free premium and economy rooms
     * @param premiumRoomGuests the list of premium room offers
     * @param economyGuests     the list of economy guests
     * @return the list of economy room guests after distributing the rest of the guests
     */
    private List<BigDecimal> distributeRestOfTheGuests(RoomOccupancyRequest request, List<BigDecimal> premiumRoomGuests,
                                                       List<BigDecimal> economyGuests) {
        var numberOfFreeEconomyRooms = request.numberOfFreeEconomyRooms();
        var economyLeftOverRooms = economyGuests.size() - numberOfFreeEconomyRooms;
        List<BigDecimal> economyRoomGuests = new ArrayList<>();

        for (BigDecimal guest : economyGuests) {
            if (economyLeftOverRooms > 0 && premiumRoomGuests.size() < request.numberOfFreePremiumRooms()) {
                premiumRoomGuests.add(guest);
                economyLeftOverRooms--;
            } else if (economyRoomGuests.size() < numberOfFreeEconomyRooms) {
                economyRoomGuests.add(guest);
            }
        }
        return economyRoomGuests;
    }
}
