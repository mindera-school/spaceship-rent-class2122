package com.mindera.school.spaceshiprent.exception.exceptionHandlers;

import com.mindera.school.spaceshiprent.exception.errorModel.ValidationError;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RequiredArgsConstructor
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(ValidationExceptionHandler.class);

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

        LOGGER.error("Validation error list : " + validationList);
        return new ResponseEntity<>(error, status);
    }

}

