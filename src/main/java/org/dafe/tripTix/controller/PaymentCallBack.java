package org.dafe.tripTix.controller;

import org.dafe.tripTix.entity.Booking;
import org.dafe.tripTix.repository.PaymentVerificationResponse;
import org.dafe.tripTix.service.BookingService;
import org.dafe.tripTix.service.PaystackService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class PaymentCallBack {

    private BookingService bookingService;
    private final PaystackService paystackService;

    public PaymentCallBack(BookingService bookingService, PaystackService paystackService) {
        this.bookingService = bookingService;
        this.paystackService = paystackService;
    }

    @GetMapping("/bookings/paystack/callback")
    public String handlePaystackCallback(@RequestParam Map<String, String> queryParams) {
        String reference = queryParams.get("reference");

        try {
            PaymentVerificationResponse verificationResponse = paystackService.paymentVerification(reference);
            if (verificationResponse != null) {
                String status = verificationResponse.getStatus();

                System.out.println("status " + status);
                System.out.println("true".equalsIgnoreCase(status));
                if ("true".equalsIgnoreCase(status)) {
                    System.out.println("If it is true");
                    Booking booking = bookingService.findByReference(reference);
                    System.out.println(booking == null);
                    if (booking != null) {
                        booking.setStatus("PAID");
                        booking.getTrip().setBooked(true);
                        bookingService.save(booking);
                    }
                    return "payment-success"; // Return the name of Thymeleaf template
                } else {
                    Booking booking = bookingService.findByReference(reference);
                    if (booking != null) {
                        booking.setStatus("FAILED");
                        bookingService.save(booking);
                    }
                    return "payment-failure"; // Return the name of Thymeleaf template
                }
            } else {
                // Handle case where verificationResponse is null (usually indicates an error)
                return "error";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // Handle any exceptions that occur during payment verification
            return "error";
        }
    }


}
