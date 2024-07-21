package org.dafe.tripTix.service;

import lombok.AllArgsConstructor;
import org.dafe.tripTix.dto.BookingRequest;
import org.dafe.tripTix.entity.Booking;
import org.dafe.tripTix.exception.ApiException;
import org.dafe.tripTix.repository.BookingsRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "bookings")
public class BookingService {
    private BookingsRepository bookingRepository;

//    @Cacheable(value = "bookings")
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

//    @Cacheable(value = "bookings", key = "#userId")
    public List<Booking> findAllByUserId(Long userId) {
        return bookingRepository.findAllByUserId(userId);
    }
//    @Cacheable(value = "bookings", key = "reference")
    public Booking findByReference(String reference) {
        Optional<Booking> optionalBooking = bookingRepository.findByReference(reference);
        return optionalBooking.orElse(null);
    }


    @Transactional
//    @CacheEvict(value = "bookings", allEntries = true)
//    @CacheEvict(value = "bookings", key = "#booking.userId")
//    @CacheEvict(value = "bookings", key = "#booking.reference")
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }

    public List<Booking> getAllBookings() {
        var all = bookingRepository.findAll();
        if (all.isEmpty()) {
            throw new ApiException("No Destination");
        }
        return all;
    }



//    public Booking createBooking(BookingRequest bookingRequest) {
//        Booking booking = new Booking();
//        booking.setUser(bookingRequest.getUser());
//        booking.setTrip(bookingRequest.getTripDetails());
//        // Set other properties as needed
//
//        return bookingRepository.save(booking);
//    }
}

