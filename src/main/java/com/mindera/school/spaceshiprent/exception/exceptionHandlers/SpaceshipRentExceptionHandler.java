package com.mindera.school.spaceshiprent.exception.exceptionHandlers;

import com.mindera.school.spaceshiprent.exception.NotFoundExceptions.RentNotFoundException;
import com.mindera.school.spaceshiprent.exception.NotFoundExceptions.UserNotFoundException;
import com.mindera.school.spaceshiprent.exception.SpaceshipRentException;
import com.mindera.school.spaceshiprent.exception.errorModel.SpaceshipRentError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class SpaceshipRentExceptionHandler extends ResponseEntityExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(SpaceshipRentExceptionHandler.class);

    @ExceptionHandler(value = {UserNotFoundException.class, SpaceshipRentException.class, RentNotFoundException.class})
    public ResponseEntity<SpaceshipRentError> handleNotFoundException(Exception ex, HttpServletRequest req) {
        SpaceshipRentError error = SpaceshipRentError.builder()
                .message(ex.getMessage())
                .exception(ex.getClass().getSimpleName())
                .path(req.getRequestURI())
                .build();
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
