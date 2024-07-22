package org.dafe.tripTix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_type_id")
    private VehicleType vehicleType;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private TransportRoute route;

    @ManyToOne
    private Terminal from;

    @ManyToOne
    private Terminal to;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "seat_id",referencedColumnName = "id")
    private Seat seat;

    private TripType tripType;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime; // Only for round trips
    private int availableSeats;
    private double price;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean booked;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean oneDayReminderSent;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean fewHoursReminderSent;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean departureReminderSent;
}
