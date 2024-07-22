package org.dafe.tripTix.service;

import lombok.AllArgsConstructor;
import org.dafe.tripTix.email.EmailService;
import org.dafe.tripTix.entity.Seat;
import org.dafe.tripTix.entity.User;
import org.dafe.tripTix.repository.SeatRepository;
import org.dafe.tripTix.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SeatService {

    private final SeatRepository seatRep;

    private final UserRepository userRepository;

    private final EmailService emailService;

    public boolean blockSeat(int seatId, int userId) {
        Optional<Seat> optionalSeat = seatRep.findById(seatId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalSeat.isPresent()) {
            Seat seat = optionalSeat.get();
            User user = optionalUser.get();
            System.out.println(seat.isAvailable());
            System.out.println(seat.isBlocked());
            if (seat.isAvailable() && !seat.isBlocked()) {
                seat.setBlocked(true);
                seat.setBlocked_at(LocalDateTime.now());
                seat.setUser(user);
                seatRep.save(seat);

                String seatType = seat.getVehicleType().getType().toString();

                LocalDateTime blocked_at = seat.getBlocked_at();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, HH:mm a");
                assert blocked_at != null;
                String blockedDate = formatter.format(blocked_at);

                emailService.sendSeatBlockedForUserEmail(user.getEmail(), user.getFullName(), seatType, blockedDate);
                return true;
            }
        }
        return false;
    }
}
