package org.dafe.tripTix.dto;


import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class BookingDTO {

    private Long id;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private String vehicleType;
    private int seatNumber;
    private Double price;


}
