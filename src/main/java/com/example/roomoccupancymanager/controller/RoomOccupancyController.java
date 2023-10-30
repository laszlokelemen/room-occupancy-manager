package com.example.roomoccupancymanager.controller;

import com.example.roomoccupancymanager.payload.RoomOccupancyRequest;
import com.example.roomoccupancymanager.payload.RoomOccupancyResponse;
import com.example.roomoccupancymanager.service.RoomOccupancyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomOccupancyController {

    private final RoomOccupancyService roomOccupancyService;

    public RoomOccupancyController(RoomOccupancyService roomOccupancyService) {
        this.roomOccupancyService = roomOccupancyService;
    }

    /**
     * Endpoint for optimizing room occupancy.
     *
     * @param request The room occupancy request.
     * @return The room occupancy response.
     */
    @PostMapping("/optimize")
    public ResponseEntity<RoomOccupancyResponse> optimize(@Valid @RequestBody RoomOccupancyRequest request) {
        return ResponseEntity.ok(roomOccupancyService.optimize(request));
    }
}
