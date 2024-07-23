package org.dafe.tripTix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class CreateVehicleRequest {
    private VehicleTypeDto vehicleType;
    private List<SeatDto> seats;
}
