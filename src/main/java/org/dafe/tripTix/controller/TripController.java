package org.dafe.tripTix.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.dafe.tripTix.entity.Terminal;
import org.dafe.tripTix.entity.Trip;
import org.dafe.tripTix.service.TripService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/trip")
@AllArgsConstructor
public class TripController {

    private TripService tripService;

    @GetMapping("/trips")
    public List<Trip> getAllTrips() {
        return tripService.findAll();
    }

    @PostMapping
    public Trip createTrip(@RequestBody Trip trip) {
        return tripService.save(trip);
    }

    @DeleteMapping("/{id}")
    public void deleteTrip(@PathVariable Long id) {
        tripService.delete(id);
    }

    @GetMapping("/search")
    public List<Trip> findTrips(@RequestParam Terminal from, @RequestParam Terminal to) {
        return tripService.findTrips(from, to);
    }

    @GetMapping("/available-trips")
    public List<Trip> getAvailableTrips() {
        return tripService.getAvailableTrips();
    }
    @PutMapping("/{id}")
    public Trip updateTrip(@PathVariable Long id, @RequestBody Trip tripDetails) {
        return tripService.updateTrip(id, tripDetails);
    }
}
