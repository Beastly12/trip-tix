package org.dafe.tripTix.controller;

import lombok.AllArgsConstructor;
import org.dafe.tripTix.dto.BookingRequest;
import org.dafe.tripTix.entity.Seat;
import org.dafe.tripTix.exception.SeatAlreadyBookedException;
import org.springframework.ui.Model;
import org.dafe.tripTix.dto.BookingPaymentDto;
import org.dafe.tripTix.dto.InitializePaymentDto;
import org.dafe.tripTix.entity.Booking;
import org.dafe.tripTix.exception.ApiException;
import org.dafe.tripTix.repository.InitializePaymentResponse;
import org.dafe.tripTix.repository.PaymentVerificationResponse;
import org.dafe.tripTix.service.BookingService;
import org.dafe.tripTix.service.PaystackService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
public class BookingsController {
    private BookingService bookingService;

    private final PaystackService paystackService;

    @GetMapping
    public List<Booking> getAllBookings() {
        try{
            return bookingService.getAllBookings();
        }
        catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
//        return bookingService.findAll();
    }

    @PostMapping("/createbookings")
    public ResponseEntity<String> createBooking(@RequestBody BookingPaymentDto bookingPaymentDto) {
        Seat seat = bookingPaymentDto.getSeat();

        if (Boolean.TRUE.equals(seat.getBooked())) {
            throw new SeatAlreadyBookedException("This seat has already been booked.");
        }

        InitializePaymentDto paymentDto = new InitializePaymentDto();
        BigDecimal _a = new BigDecimal("100");
        BigDecimal amount = bookingPaymentDto.getAmount().multiply(_a);

        paymentDto.setAmount(amount);
        String callbackUrl = "http://localhost:8090/bookings/paystack/callback";
        paymentDto.setCallback_url(callbackUrl);
        paymentDto.setEmail(bookingPaymentDto.getEmail());

        InitializePaymentResponse paymentResponse = paystackService.initializePayment(paymentDto);
        if (paymentResponse == null || !paymentResponse.isStatus()) {
            throw new ApiException("Payment initialization failed: " + paymentResponse.getMessage());
        }

        Booking booking = new Booking();
        booking.setTrip(bookingPaymentDto.getTrip());
        booking.setUser(bookingPaymentDto.getUser());
        booking.setReference(paymentResponse.getData().getReference());
        booking.setAmount(bookingPaymentDto.getAmount());
        booking.setNoOfAdults(bookingPaymentDto.getNoOfAdults());
        booking.setNoOfChildren(bookingPaymentDto.getNoOfChildren());
        booking.setBookedSeat(bookingPaymentDto.getBookedSeat());

        seat.setBooked(true);
        seat.setBooked_at(LocalDateTime.now()); // Set booked timestamp if needed

        // Save the updated seat entity
        bookingService.saveseat(seat);

        booking.setSeat(seat);
        // Save booking only after successful payment verification
        booking.setStatus("PENDING"); // Assume initial status
        bookingService.save(booking);

        // Redirect to authorization URL for payment
        return ResponseEntity.ok(paymentResponse.getData().getAuthorizationUrl());
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        bookingService.delete(id);
    }


    @GetMapping("/verify-payment/{reference}")
    public PaymentVerificationResponse verifyPayment(@PathVariable String reference, @RequestParam Long bookingId) {
        try {
            return paystackService.paymentVerification(reference);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @GetMapping("/test")
    public String testing(){
        return "test";
    }


}
