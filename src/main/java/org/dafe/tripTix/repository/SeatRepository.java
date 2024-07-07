package org.dafe.tripTix.repository;


import org.dafe.tripTix.entity.Seat;
import org.dafe.tripTix.entity.Trip;
import org.dafe.tripTix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByTripAndIsAvailable(Trip trip, boolean isAvailable);

    List<Seat> findByUser(User user);
}