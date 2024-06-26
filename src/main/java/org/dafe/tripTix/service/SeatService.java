package org.dafe.tripTix.service;

import lombok.RequiredArgsConstructor;
import org.dafe.tripTix.entity.Seat;
import org.dafe.tripTix.entity.Trip;
import org.dafe.tripTix.exception.ApiException;
import org.dafe.tripTix.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final TripService tripService;

    public List<Seat> getAvailableSeats(Long tripId) {
        Trip trip = tripService.getTripById(tripId);
        return seatRepository.findByTripAndIsAvailable(trip, true);
    }

    public List<Seat> selectSeats(Long tripId, List<Long> seatIds) {
        Trip trip = tripService.getTripById(tripId);
        int seatLimit = getSeatLimitByVehicleType(trip.getVehicle().getVehicleType().toString());

        if (seatIds.size() > seatLimit) {
            throw new ApiException("Cannot select more than " + seatLimit + " seats for this vehicle type");
        }

        List<Seat> selectedSeats = seatRepository.findAllById(seatIds);
        if (selectedSeats.size() != seatIds.size() || selectedSeats.stream().anyMatch(seat -> !seat.isAvailable())) {
            throw new ApiException("Some seats are not available");
        }

        selectedSeats.forEach(seat -> seat.setAvailable(false));
        return seatRepository.saveAll(selectedSeats);
    }

    private int getSeatLimitByVehicleType(String vehicleType) {
        switch (vehicleType.toLowerCase()) {
            case "car":
                return 4;
            case "minibus":
                return 5;
            case "regular bus":
                return 5;
            default:
                throw new RuntimeException("Unknown vehicle type: " + vehicleType);
        }
    }
}
