package com.laszlokelemen.roomoccupancymanager.service;

import com.laszlokelemen.roomoccupancymanager.payloads.RoomOccupancyRequest;
import com.laszlokelemen.roomoccupancymanager.payloads.RoomOccupancyResponse;

public interface RoomOccupancyService {
    /**
     * Optimizes the room occupancy based on the given request.
     *
     * @param request the request containing the room occupancy details
     * @return the response containing the optimized room occupancy
     */
    RoomOccupancyResponse optimize(RoomOccupancyRequest request);
}
