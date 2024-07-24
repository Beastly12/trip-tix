package org.dafe.tripTix.controller;


import org.dafe.tripTix.dto.BookingDTO;
import org.dafe.tripTix.dto.ContactUsDto;
import org.dafe.tripTix.email.EmailService;
import org.dafe.tripTix.entity.Booking;
import org.dafe.tripTix.entity.Contactus;
import org.dafe.tripTix.entity.User;
import org.dafe.tripTix.service.BookingService;
import org.dafe.tripTix.service.ContactUsService;
import org.dafe.tripTix.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final UserService userService;
    private final ContactUsService contactUsService;
    private final EmailService emailService;
    private final BookingService bookingService;

    public UserController(UserService userService, ContactUsService contactUsService, EmailService emailService, BookingService bookingService) {
        this.userService = userService;
        this.contactUsService = contactUsService;
        this.emailService = emailService;
        this.bookingService = bookingService;
    }

    @GetMapping("/user/bookings")
    public List<BookingDTO> getBookingsForUser(@RequestParam int userId) {
        // For simplicity, assuming you fetch the user from userId. This might be handled differently in your application.
        User user = new User(); // Populate user object based on userId or authentication context
        user.setId(userId);
        return bookingService.getAllBookingsForUser(user);
    }
    @GetMapping("/user")
    public ResponseEntity<?> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/getAllContactus")
    public ResponseEntity<List> submitContactUsForm() {

        List<Contactus> contactusList = contactUsService.findAll();
        if (contactusList.isEmpty()){
            return ResponseEntity.ok(contactusList);
        }
        return ResponseEntity.ok(contactusList);
    }


    @PostMapping("/submit/contactus")
    public ResponseEntity<String> submitContactUsForm(@RequestBody ContactUsDto contactUsDto) {
        List<User> adminUsers = userService.findAllAdmins();

        // Notify all admin users
        adminUsers.forEach(admin -> {
            emailService.sendContactUsNotificationEmail(
                    admin.getEmail(),
                    contactUsDto.getFullName(),
                    contactUsDto.getEmail(),
                    contactUsDto.getPhoneNumber(),
                    contactUsDto.getSubject(),
                    contactUsDto.getMessage()
            );
        });

        Contactus contactus = new Contactus(
                contactUsDto.getFullName(),
                contactUsDto.getEmail(),
                contactUsDto.getPhoneNumber(),
                contactUsDto.getSubject(),
                contactUsDto.getMessage()
        );

        contactUsService.save(contactus);

        return ResponseEntity.ok("Message sent to all admins.");
    }

}
