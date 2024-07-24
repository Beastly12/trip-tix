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
    public String confirm(@RequestParam("userid") int userid) {
        int isEmailConfirmed = userService.enableUserEmail(userid);

        String header = "<html><head><style>" +
                "body { font-family: Arial, sans-serif; line-height: 1.6; padding: 20px; margin: 0; }" +
                "h1 { color: #4CAF50; }" +
                "p { font-size: 16px; color: #333; }" +
                "div { max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; }" +
                "</style></head><body><div>";

        String footer = "</div></body></html>";

        if (isEmailConfirmed == 1) {
            return header + "<h1>Success</h1><p>Your email has been successfully confirmed. Thank you!</p>" + footer;
        } else {
            return header + "<h1>Error</h1><p>Failed to confirm email. Please try again.</p>" + footer;
        }
    }

}