package com.mindera.school.spaceshiprent.exception.exceptionHandlers;

import com.mindera.school.spaceshiprent.exception.SpaceshipRentException;
import com.mindera.school.spaceshiprent.exception.exceptions.RentNotFoundException;
import com.mindera.school.spaceshiprent.exception.exceptions.UnavailableRentDatesException;
import com.mindera.school.spaceshiprent.exception.exceptions.UserAlreadyExists;
import com.mindera.school.spaceshiprent.exception.exceptions.UserNotFoundException;
import com.mindera.school.spaceshiprent.exception.model.SpaceshipRentError;
import com.mindera.school.spaceshiprent.exception.model.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class SpaceshipRentExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            UserNotFoundException.class,
            SpaceshipRentException.class,
            RentNotFoundException.class,
    })
    public ResponseEntity<SpaceshipRentError> handleNotFoundException(Exception ex, HttpServletRequest req) {
        SpaceshipRentError error = SpaceshipRentError.builder()
                .message(ex.getMessage())
                .exception(ex.getClass().getSimpleName())
                .path(req.getRequestURI())
                .build();
        log.error(ex.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {
            UnavailableRentDatesException.class,
            UserAlreadyExists.class
    })
    public ResponseEntity<SpaceshipRentError> handleBadRequestException(Exception ex, HttpServletRequest req) {
        SpaceshipRentError error = SpaceshipRentError.builder()
                .message(ex.getMessage())
                .exception(ex.getClass().getSimpleName())
                .path(req.getRequestURI())
                .build();
        log.error(ex.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> validationList = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ValidationError error = ValidationError.builder()
                .failedValidationsList(validationList)
                .exception(ex.getClass().getSimpleName())
                .build();

        log.error("Validation error list : " + validationList);
        return new ResponseEntity<>(error, status);
    }
}
