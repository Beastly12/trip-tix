package org.dafe.tripTix.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.dafe.tripTix.entity.Terminal;
import org.dafe.tripTix.entity.Trip;
import org.dafe.tripTix.service.SeatService;
import org.dafe.tripTix.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/trip")
public class TripController {

    private final TripService tripService;
    private final SeatService seatService;

    public TripController(TripService tripService, SeatService seatService) {
        this.tripService = tripService;
        this.seatService = seatService;
    }

    @PostMapping("/block")
    public ResponseEntity<String> blockSeat(@RequestParam int seatId, @RequestParam int userId) {
        boolean blocked = seatService.blockSeat(seatId, userId);
        if (blocked) {
            return ResponseEntity.ok("Seat successfully blocked.");
        } else {
            return ResponseEntity.badRequest().body("Seat could not be blocked. It might be already blocked or not available.");
        }
    }

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
