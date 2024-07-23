package org.dafe.tripTix.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class SeatDto {
    private boolean available;
    private Boolean booked;
    private Boolean blocked;
}
