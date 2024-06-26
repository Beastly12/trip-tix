package org.dafe.tripTix.controller;


import lombok.RequiredArgsConstructor;
import org.dafe.tripTix.entity.Location;
import org.dafe.tripTix.service.LocationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping("/all")
    public List<Location> findAll() {
        return locationService.findAll();
    }

    @GetMapping("/{id}")
    public Location findById(@PathVariable Long id) {
        return locationService.findById(id);
    }

    @PostMapping
    public Location save(@RequestBody Location location) {
        return locationService.save(location);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        locationService.deleteById(id);
    }
}