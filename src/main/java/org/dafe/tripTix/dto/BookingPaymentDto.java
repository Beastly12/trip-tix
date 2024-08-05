package org.dafe.tripTix.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dafe.tripTix.entity.Seat;
import org.dafe.tripTix.entity.Trip;
import org.dafe.tripTix.entity.User;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BookingPaymentDto {

    @NotNull(message = "Trip cannot be null")
    @JsonProperty("trip")
    private Trip trip;

    @NotNull(message = "Seat cannot be null")
    @JsonProperty("seat")
    private Seat seat;

    @NotNull(message = "User cannot be null")
    @JsonProperty("user")
    private User user;

    @NotNull(message = "Email cannot be null")
    @JsonProperty("email")
    private String email;

    private int noOfAdults;
    private int noOfChildren;
    private String bookedSeat;
}
