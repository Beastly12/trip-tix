package org.dafe.tripTix.repository;

import org.dafe.tripTix.entity.Seat;
import org.dafe.tripTix.entity.Tickets;
import org.dafe.tripTix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketsRepository extends JpaRepository<Tickets, Integer> {
    List<Tickets> findByUser(User user);
}