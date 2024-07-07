package org.dafe.tripTix.service;


import lombok.RequiredArgsConstructor;
import org.dafe.tripTix.entity.*;
import org.dafe.tripTix.exception.ApiException;
import org.dafe.tripTix.repository.TripRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;


import java.util.List;

@Service
@RequiredArgsConstructor
@CrossOrigin

public class TripService {
    private final TripRepository tripRepository;

    public List<Trip> getAvailableTrips() {
        return tripRepository.findAll();
    }
    public List<Trip> findTrips(Location from, Destination to) {
        return tripRepository.findByFromAndTo(from, to);
    }

    public List<Trip> filterTrips(Location from, Destination to, String vehicleType) {
        return tripRepository.findByFrom_NameAndTo_NameAndVehicle_VehicleType_Type(from.getName(), to.getName(), vehicleType);
    }

    public Trip getTripById(Long id) {
        return tripRepository.findById(id).orElseThrow(() -> new ApiException("Trip not found"));
    }

    public List<Trip> getTripsByUser(User user) {
        return tripRepository.findByUser(user);
    }

}