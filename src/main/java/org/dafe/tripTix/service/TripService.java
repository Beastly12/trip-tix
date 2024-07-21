package org.dafe.tripTix.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.dafe.tripTix.entity.Terminal;
import org.dafe.tripTix.entity.Trip;
import org.dafe.tripTix.exception.ApiException;
import org.dafe.tripTix.exception.ResourceNotFoundException;
import org.dafe.tripTix.repository.TripRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "bookings")
public class TripService {

    private final TripRepository tripRepository;
    private final HttpServletRequest request;

    @Cacheable(value = "bookings", unless = "#result.isEmpty() || #root.target.isAuthenticated()")
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    @CacheEvict(value = "bookings", allEntries = true)
    public Trip save(Trip trip) {
        return tripRepository.save(trip);
    }

    @CacheEvict(value = "bookings", allEntries = true)
    public void delete(Long id) {
        tripRepository.deleteById(id);
    }

    @Cacheable(value = "bookings", unless = "#result.isEmpty() || #root.target.isAuthenticated()")
    public List<Trip> getAvailableTrips() {
        return tripRepository.findAll();
    }

    public Trip getTripById(Long id) {
        return tripRepository.findById(id).orElseThrow(() -> new ApiException("Trip not found"));
    }

    @Cacheable(value = "bookings", key = "#from.id + '-' + #to.id", unless = "#result.isEmpty() || #root.target.isAuthenticated()")
    public List<Trip> findTrips(Terminal from, Terminal to) {
        return tripRepository.findByFromAndTo(from, to);
    }

    @CacheEvict(value = "bookings", allEntries = true)
    @Transactional
    public Trip updateTrip(Long id, Trip tripDetails) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + id));

        // Check if the trip has been booked
        if (trip.getBooked()) {
            throw new IllegalStateException("Cannot update a trip that has already been booked.");
        }

        // Update trip details
        trip.setAvailableSeats(tripDetails.getAvailableSeats());
        trip.setPrice(tripDetails.getPrice());
        trip.setTripType(tripDetails.getTripType());
        trip.setArrivalDateTime(tripDetails.getArrivalDateTime());
        trip.setDepartureDateTime(tripDetails.getDepartureDateTime());
        trip.setRoute(tripDetails.getRoute());
        trip.setVehicleType(tripDetails.getVehicleType());

        return tripRepository.save(trip);
    }
    public boolean isAuthenticated() {
        return (boolean) request.getAttribute("isAuthenticated");
    }
}
