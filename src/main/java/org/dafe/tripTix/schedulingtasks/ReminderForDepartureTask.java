package org.dafe.tripTix.schedulingtasks;

import lombok.AllArgsConstructor;
import org.dafe.tripTix.email.EmailService;
import org.dafe.tripTix.entity.Trip;
import org.dafe.tripTix.repository.TripRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@AllArgsConstructor
public class ReminderForDepartureTask {

    private final TripRepository tripRepository;
    private final EmailService emailService;

    @Scheduled(fixedRate = 3600000)
    public void sendReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneDayFromNow = now.plusDays(1);
        LocalDateTime fewHoursFromNow = now.plusHours(2);

        List<Trip> trips = tripRepository.findAll();

        for (Trip trip : trips) {
            LocalDateTime departureDateTime = trip.getDepartureDateTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, HH:mm a");

            System.out.println(now);
            System.out.println(departureDateTime);
            if (trip.getBooked()) {
                if (!trip.getDepartureReminderSent() && departureDateTime.toLocalDate().equals(now.toLocalDate())) {
                    emailService.sendTripTodayReminderEmail(trip.getUser().getEmail(), trip.getUser().getFullName(), trip, formatter.format(departureDateTime));
                    trip.setDepartureReminderSent(true);
                    tripRepository.save(trip);
                }
                else if (!trip.getFewHoursReminderSent() && departureDateTime.isAfter(now) && departureDateTime.isBefore(fewHoursFromNow)) {
                    emailService.sendTripReminderEmail(trip.getUser().getEmail(), trip.getUser().getFullName(), trip, "in a few hours", formatter.format(departureDateTime));
                    trip.setFewHoursReminderSent(true);
                    tripRepository.save(trip);
                }
                else if (!trip.getOneDayReminderSent() && departureDateTime.isAfter(now) && departureDateTime.isBefore(oneDayFromNow)) {
                    emailService.sendTripReminderEmail(trip.getUser().getEmail(), trip.getUser().getFullName(), trip, "in a day", formatter.format(departureDateTime));
                    trip.setOneDayReminderSent(true);
                    tripRepository.save(trip);
                }
            }


        }
    }
}
