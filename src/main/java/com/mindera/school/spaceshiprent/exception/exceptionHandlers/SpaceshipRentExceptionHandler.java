package com.mindera.school.spaceshiprent.exception.exceptionHandlers;

import com.mindera.school.spaceshiprent.exception.exceptions.RentAlreadyPickedUpException;
import com.mindera.school.spaceshiprent.exception.exceptions.RentAlreadyReturnedException;
import com.mindera.school.spaceshiprent.exception.exceptions.RentNotFoundException;
import com.mindera.school.spaceshiprent.exception.exceptions.RentNotPickedUpException;
import com.mindera.school.spaceshiprent.exception.exceptions.UserNotFoundException;
import com.mindera.school.spaceshiprent.exception.SpaceshipRentException;
import com.mindera.school.spaceshiprent.exception.exceptions.WrongCredentialsException;
import com.mindera.school.spaceshiprent.exception.model.SpaceshipRentError;
import com.mindera.school.spaceshiprent.exception.model.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @ExceptionHandler(value = {UserNotFoundException.class, SpaceshipRentException.class, RentNotFoundException.class})
    public ResponseEntity<SpaceshipRentError> handleNotFoundException(Exception ex, HttpServletRequest req) {
        return buildDefaultErrorResponseEntity(ex, req, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {WrongCredentialsException.class})
    public ResponseEntity<SpaceshipRentError> handleUnauthorizedException(Exception ex, HttpServletRequest req) {
        return buildDefaultErrorResponseEntity(ex, req, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {RentAlreadyPickedUpException.class, RentNotPickedUpException.class,
            RentAlreadyReturnedException.class})
    public ResponseEntity<SpaceshipRentError> handleConflictException(Exception ex, HttpServletRequest req) {
        return buildDefaultErrorResponseEntity(ex, req, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<SpaceshipRentError> handleAllExceptions(Exception ex, HttpServletRequest req) {
        return buildDefaultErrorResponseEntity(ex, req, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<SpaceshipRentError> buildDefaultErrorResponseEntity(Exception ex, HttpServletRequest req, HttpStatus status) {
        SpaceshipRentError error = SpaceshipRentError.builder()
                .message(ex.getMessage())
                .exception(ex.getClass().getSimpleName())
                .path(req.getRequestURI())
                .build();
        return new ResponseEntity<>(error, new HttpHeaders(), status);
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

        log.error("Validation error list: " + validationList);
        return new ResponseEntity<>(error, status);
    }
}
