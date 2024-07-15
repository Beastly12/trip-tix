package org.dafe.tripTix.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dafe.tripTix.entity.Trip;
import org.dafe.tripTix.entity.User;

import java.math.BigDecimal;

@Data
public class BookingPaymentDto {

    @NotNull(message = "Trip cannot be null")
    @JsonProperty("trip")
    private Trip trip;

    @NotNull(message = "User cannot be null")
    @JsonProperty("user")
    private User user;

    @NotNull(message = "Amount cannot be null")
    @JsonProperty("amount")
    private BigDecimal amount;

    @NotNull(message = "Email cannot be null")
    @JsonProperty("email")
    private String email;

    private int noOfAdults;
    private int noOfChildren;
    private String bookedSeat;
}
