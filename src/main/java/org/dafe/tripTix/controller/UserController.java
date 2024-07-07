package org.dafe.tripTix.controller;


import org.dafe.tripTix.email.EmailService;
import org.dafe.tripTix.entity.Seat;
import org.dafe.tripTix.entity.Tickets;
import org.dafe.tripTix.entity.Trip;
import org.dafe.tripTix.entity.User;
import org.dafe.tripTix.service.SeatService;
import org.dafe.tripTix.service.TripService;
import org.dafe.tripTix.service.UserService;
import org.dafe.tripTix.service.impl.TicketsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {


    private final TripService tripService;
    private final TicketsServiceImpl ticketsService;
    private final SeatService seatService;

    public UserController(TripService tripService, TicketsServiceImpl ticketsService, SeatService seatService) {
        this.tripService = tripService;
        this.ticketsService = ticketsService;
        this.seatService = seatService;
    }

    @GetMapping("/user")
    public ResponseEntity<?> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/userTrips")
    public List<Trip> getTripsForCurrentUser() {
        // Retrieve the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        // Fetch trips for the authenticated user
        return tripService.getTripsByUser(currentUser);
    }

    @GetMapping("/userTickets")
    public List<Tickets> getTicketsForCurrentUser() {
        // Retrieve the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        // Fetch trips for the authenticated user
        return ticketsService.getTicketsByUser(currentUser);
    }

    @GetMapping("/userSeats")
    public List<Seat> getSeatsForCurrentUser() {
        // Retrieve the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        // Fetch trips for the authenticated user
        return seatService.getSeatsByUser(currentUser);
    }

}
