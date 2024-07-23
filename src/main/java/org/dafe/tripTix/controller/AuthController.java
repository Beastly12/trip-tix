package org.dafe.tripTix.controller;

import jakarta.validation.Valid;
import org.dafe.tripTix.dto.LoginDto;
import org.dafe.tripTix.dto.LoginResponse;
import org.dafe.tripTix.dto.SignUpDto;
import org.dafe.tripTix.email.EmailService;
import org.dafe.tripTix.entity.User;
import org.dafe.tripTix.service.JwtService;
import org.dafe.tripTix.service.RegisterationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final static Logger logger = LoggerFactory.getLogger(EmailService.class);

    private JwtService authService;

    private RegisterationService userService;

    public AuthController(JwtService authService, RegisterationService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@Valid @RequestBody SignUpDto registerUserDto) {
        logger.info("Received signup request for: {}", registerUserDto.getEmail());

        User registeredUser = userService.register(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDto loginUserDto) {
        User authenticatedUser = userService.authenticate(loginUserDto);



        String jwtToken = authService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(jwtToken,authService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }


    @GetMapping("/confirmEmail")
    public int confirm(@RequestParam("userid") int userid) {
        return userService.enableUserEmail(userid);
    }

}