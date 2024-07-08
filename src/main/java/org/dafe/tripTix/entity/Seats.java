package org.dafe.tripTix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seats")
public class Seats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int seatId;

    @Column(nullable = false)
    private int availableSeats;

    @Column(nullable = false)
    private int selectedSeats;

    @Column(nullable = false)
    private int bookedSeats;

    @Column(nullable = false)
    private int blockedSeats;

    @OneToMany(mappedBy = "seat")
    private List<VehicleSeats> vehicleSeats;

}