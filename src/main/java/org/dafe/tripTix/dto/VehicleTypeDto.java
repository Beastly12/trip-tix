package org.dafe.tripTix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.dafe.tripTix.entity.EType;

import java.util.Set;

@Data
@AllArgsConstructor
@ToString
public class VehicleTypeDto {
    private Set<EType> type;
    private int capacity;
    private double price;
    private Set<SeatDto> seats;
}
