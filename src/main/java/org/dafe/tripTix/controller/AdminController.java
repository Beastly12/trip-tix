package org.dafe.tripTix.controller;

import lombok.AllArgsConstructor;
import org.dafe.tripTix.dto.CreateVehicleRequest;
import org.dafe.tripTix.dto.VehicleTypeDto;
import org.dafe.tripTix.entity.VehicleType;
import org.dafe.tripTix.service.BookingService;
import org.dafe.tripTix.service.TripService;
import org.dafe.tripTix.service.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private VehicleTypeService vehicleTypeService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TripService tripService;

    @GetMapping("/total-booked")
    public long getTotalBookedTrips() {
        return tripService.getTotalBookedTrips();
    }

    @GetMapping("/total-paid-amount")
    public BigDecimal getTotalPaidAmount() {
        return bookingService.getTotalAmountPaid();
    }

    @PostMapping("/createVehicle")
    public ResponseEntity<VehicleType> createVehicle(@RequestBody CreateVehicleRequest request) {
        VehicleType vehicle = vehicleTypeService.createVehicle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicle);
    }

}
