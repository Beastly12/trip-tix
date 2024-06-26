package org.dafe.tripTix.controller;

import lombok.RequiredArgsConstructor;
import org.dafe.tripTix.entity.Destination;
import org.dafe.tripTix.entity.Location;
import org.dafe.tripTix.entity.Trip;
import org.dafe.tripTix.service.TripService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/trips")
public class TripController {
    private final TripService tripService;

    @GetMapping("/available")
    public List<Trip> getAvailableTrips() {
        return tripService.getAvailableTrips();
    }

    @GetMapping("/search")
    public List<Trip> findTrips(@RequestParam Location from, @RequestParam Destination to) {
        return tripService.findTrips(from, to);
    }

    @GetMapping("/filter")
    public List<Trip> filterTrips(@RequestParam Location from, @RequestParam Destination to, @RequestParam String vehicleType) {
        return tripService.filterTrips(from, to, vehicleType);
    }

    @GetMapping("/{id}")
    public Trip getTripById(@PathVariable Long id) {
        return tripService.getTripById(id);
    }
}
