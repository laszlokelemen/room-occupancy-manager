package com.example.roomoccupancymanager.service;

import com.example.roomoccupancymanager.payload.RoomOccupancyRequest;
import com.example.roomoccupancymanager.payload.RoomOccupancyResponse;

public interface RoomOccupancyService {
    /**
     * Optimizes the room occupancy based on the given request.
     *
     * @param request the request containing the room occupancy details
     * @return the response containing the optimized room occupancy
     */
    RoomOccupancyResponse optimize(RoomOccupancyRequest request);
}
