package org.dafe.tripTix.repository;

import org.dafe.tripTix.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Integer> {

    @Query("SELECT s FROM Seat s WHERE s.blocked = true AND s.blocked_at < :blockedAt")
    List<Seat> findExpiredBlockedSeats(@Param("blockedAt") LocalDateTime blockedAt);

}
