package com.mindera.school.spaceshiprent.exception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationError {
    private List<String> failedValidationsList;
    private String exception;
}
