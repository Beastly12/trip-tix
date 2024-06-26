package org.dafe.tripTix.service;

import lombok.RequiredArgsConstructor;
import org.dafe.tripTix.entity.Location;
import org.dafe.tripTix.exception.ApiException;
import org.dafe.tripTix.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public List<Location> findAll() {
        var all = locationRepository.findAll();
        if (all.isEmpty()) {
            throw new ApiException("No Location");
        }
        return all;
    }

    public Location findById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new ApiException("Location not found"));
    }

    public Location save(Location location) {
        return locationRepository.save(location);
    }

    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }
}
