package org.dafe.tripTix.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private boolean available;

    @Nullable
    private Boolean booked;

    @Nullable
    private Boolean blocked;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "blocked_by",referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "vehicle_type_id", referencedColumnName = "id")
    private VehicleType vehicleType;

    @Nullable
    private LocalDateTime booked_at;

    @Nullable
    private LocalDateTime blocked_at;

    public boolean isBlocked() {
        return Boolean.TRUE.equals(this.blocked);
    }
}
