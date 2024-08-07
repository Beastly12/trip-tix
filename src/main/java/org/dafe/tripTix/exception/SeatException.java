package org.dafe.tripTix.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SeatException extends RuntimeException {
    public SeatException(String message) {
        super(message);
    }
}