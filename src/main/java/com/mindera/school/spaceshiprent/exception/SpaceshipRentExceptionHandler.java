package com.mindera.school.spaceshiprent.exception;

import com.mindera.school.spaceshiprent.service.rentService.RentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class SpaceshipRentExceptionHandler extends ResponseEntityExceptionHandler {


    private static final Logger LOGGER = LoggerFactory.getLogger(SpaceshipRentExceptionHandler.class);

    @ExceptionHandler(value = {UserNotFoundException.class, SpaceshipNotFoundException.class, RentNotFoundException.class})
    public ResponseEntity<SpaceshipError> handleNotFoundException(Exception ex, HttpServletRequest req) {
        SpaceshipError error = SpaceshipError.builder()
                .message(ex.getMessage())
                .exception(ex.getClass().getSimpleName())
                .path(req.getRequestURI())
                .build();

        LOGGER.error(ex.getMessage() + " | ERROR: " + ex.getClass().getSimpleName() +" | PATH: "+ req.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
