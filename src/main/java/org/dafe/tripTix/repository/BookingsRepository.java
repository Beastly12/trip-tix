package org.dafe.tripTix.repository;

import org.dafe.tripTix.entity.Booking;
import org.dafe.tripTix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingsRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByReference(String reference);

    List<Booking> findAllByUserId(Long userId);

    @Query("SELECT SUM(b.amount) FROM Booking b WHERE b.status = 'PAID'")
    BigDecimal sumAmountByPaidStatus();

    @Query("SELECT b FROM Booking b " +
            "JOIN b.trip t " +
            "JOIN t.vehicleType v " +
            "JOIN b.seat s " +
            "WHERE b.user = :user")
    List<Booking> findAllBookingsByUser(@Param("user") User user);
}
