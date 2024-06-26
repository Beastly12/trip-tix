package org.dafe.tripTix.controller;


import lombok.RequiredArgsConstructor;
import org.dafe.tripTix.entity.Destination;
import org.dafe.tripTix.exception.ApiException;
import org.dafe.tripTix.service.DestinationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/destinations")
public class DestinationController {
    private final DestinationService destinationService;

    @GetMapping("/all")
    public List<Destination> findAll() {
        try {
            return destinationService.findAll();
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Destination findById(@PathVariable Long id) {
        try {
            return destinationService.findById(id);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @PostMapping
    public Destination save(@RequestBody Destination destination) {
        try {
            return destinationService.save(destination);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        try {
            destinationService.deleteById(id);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }
}