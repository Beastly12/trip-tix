package org.dafe.tripTix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tickets")
public class Tickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int ticketId;

    @Column(name = "location_from", nullable = false, length = 50)
    private String locationFrom;

    @Column(name = "location_to", nullable = false, length = 50)
    private String locationTo;

    @Column(name = "trip_type", nullable = false, length = 20)
    private String tripType;

    @Column(name = "departs_day", nullable = false)
    private LocalDate departsDay;

    @Column(name = "departs_time", nullable = false)
    private LocalTime departsTime;

    @Column(name = "return_day", nullable = false)
    private LocalDate returnDay;

    @Column(name = "return_time", nullable = false)
    private LocalTime returnTime;

    @Column(name = "adults", nullable = false)
    private int adults;

    @Column(name = "children", nullable = false)
    private int children;

    @Column(name = "adult_price", nullable = false)
    private float adultPrice;

    @Column(name = "child_price", nullable = false)
    private float childPrice;

    @Column(name = "passengers", nullable = false)
    private int passengers;

    @OneToMany(mappedBy = "ticket")
    private List<VehicleTicket> vehicleTickets;

    @OneToMany(mappedBy = "ticket")
    private List<VehicleSeats> vehicleSeats;

    @OneToMany(mappedBy = "ticket")
    private List<DepartureTicket> departureTickets;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
}
