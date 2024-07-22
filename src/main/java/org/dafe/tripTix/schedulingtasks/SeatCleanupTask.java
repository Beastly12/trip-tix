package org.dafe.tripTix.schedulingtasks;

import lombok.AllArgsConstructor;
import org.dafe.tripTix.email.EmailService;
import org.dafe.tripTix.entity.Seat;
import org.dafe.tripTix.repository.SeatRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@AllArgsConstructor
public class SeatCleanupTask {

    private SeatRepository seatRep;

    private EmailService emailService;

    @Scheduled(fixedRate = 360000)
    public void deleteExpiredBlockedTrips() {

        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
        List<Seat> expiredBlockedSeats = seatRep.findExpiredBlockedSeats(twentyFourHoursAgo);

        if (!expiredBlockedSeats.isEmpty()) {
            for (Seat seat : expiredBlockedSeats) {

                assert seat.getUser() != null;
                String userEmail = seat.getUser().getEmail();
                String userName = seat.getUser().getFullName();
                String seatType = seat.getVehicleType().getType().toString();

                LocalDateTime blocked_at = seat.getBlocked_at();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, HH:mm a");
                assert blocked_at != null;
                String blockedDate = formatter.format(blocked_at);
                emailService.sendSeatUnblockedEmail(userEmail, userName,seatType,blockedDate);

                seat.setBlocked_at(null);
                seat.setBlocked(false);
                seat.setUser(null);
                seatRep.save(seat);
            }
        }
    }
}
