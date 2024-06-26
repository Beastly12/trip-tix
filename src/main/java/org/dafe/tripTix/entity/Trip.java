package org.dafe.tripTix.entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    @JoinColumn(name = "location_id",referencedColumnName = "id")
    private Location from;

    @ManyToOne
    @JoinColumn(name = "destination_id",referencedColumnName = "id")
    private Destination to;

    @ManyToOne
    @JoinColumn(name = "vehicle_id",referencedColumnName = "id")
    private Vehicles vehicle;

    //  UPDATE
    @OneToMany(mappedBy = "trip")
    private List<Seat> seats;

    private LocalDateTime departureTime;
    private LocalDateTime estimatedArrivalTime;
}
