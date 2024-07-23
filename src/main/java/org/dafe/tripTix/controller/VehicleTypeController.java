package org.dafe.tripTix.controller;

import lombok.RequiredArgsConstructor;
import org.dafe.tripTix.entity.VehicleType;
import org.dafe.tripTix.service.VehicleTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/vehicle-type")
public class VehicleTypeController {

    private final VehicleTypeService vehicleTypeService;

    public VehicleTypeController(VehicleTypeService vehicleTypeService) {
        this.vehicleTypeService = vehicleTypeService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleType>> getAllVehicles() {
        List<VehicleType> vehicles = vehicleTypeService.findAll();
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping
    public VehicleType createVehicleType(@RequestBody VehicleType vehicleType) {
        return vehicleTypeService.save(vehicleType);
    }

    @DeleteMapping("/{id}")
    public void deleteVehicleType(@PathVariable Long id) {
        vehicleTypeService.delete(id);
    }
}
