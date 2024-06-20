package com.example.triptix.controller;

import com.example.triptix.model.VehicleType;
import com.example.triptix.service.VehicleTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vehicle-types")
@RequiredArgsConstructor
public class VehicleTypeController {
    private final VehicleTypeService vehicleTypeService;

    @GetMapping("/all")
    public List<VehicleType> findAll() {
        return vehicleTypeService.findAll();
    }

    @GetMapping("/{id}")
    public VehicleType findById(@PathVariable Long id) {
        return vehicleTypeService.findById(id);
    }

    @PostMapping
    public VehicleType save(@RequestBody VehicleType vehicleType) {
        return vehicleTypeService.save(vehicleType);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        vehicleTypeService.deleteById(id);
    }
}

