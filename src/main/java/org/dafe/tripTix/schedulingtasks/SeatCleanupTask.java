package org.dafe.tripTix.schedulingtasks;

import lombok.AllArgsConstructor;
import org.dafe.tripTix.email.EmailService;
import org.dafe.tripTix.entity.Seat;
import org.dafe.tripTix.repository.SeatRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class SeatCleanupTask {

    private SeatRepository seatRep;

    private EmailService emailService;

    @Scheduled(fixedRate = 1000) // Run every hour
    public void deleteExpiredBlockedTrips() {
        System.out.println("Scheduled task is running...");

        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
        List<Seat> expiredBlockedSeats = seatRep.findExpiredBlockedSeats(twentyFourHoursAgo);

        if (!expiredBlockedSeats.isEmpty()) {
            for (Seat seat : expiredBlockedSeats) {

                assert seat.getUser() != null;
                String userEmail = seat.getUser().getEmail();
                String userName = seat.getUser().getFullName();

                emailService.sendSeatUnblockedEmail(userEmail, userName);

                seatRep.delete(seat);
            }
        }
    }
}
