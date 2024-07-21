package org.dafe.tripTix.cleanup;

import jakarta.transaction.Transactional;
import org.dafe.tripTix.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TripCleanUp {

    @Autowired
    private TripRepository tripRepository;

    @Scheduled(fixedRate = 3600000) // Runs every hour
    @Transactional
    public void deleteUnbookedTrips() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);
        tripRepository.deleteByCreatedAtBeforeAndBookedFalse(cutoffTime);
    }
}
