package org.dafe.tripTix.repository;

import org.dafe.tripTix.entity.Terminal;
import org.dafe.tripTix.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByFromAndTo(Terminal from, Terminal to);

    List<Trip> findByDepartureDateTimeBetweenAndOneDayReminderSentFalse(LocalDateTime start, LocalDateTime end);
    List<Trip> findByDepartureDateTimeBetweenAndFewHoursReminderSentFalse(LocalDateTime start, LocalDateTime end);

    void deleteByCreatedAtBeforeAndBookedFalse(LocalDateTime cutoffTime);
}
