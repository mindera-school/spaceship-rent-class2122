package com.mindera.school.spaceshiprent.exception.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SpaceshipRentError {
    private String message;
    private String exception;
    private String path;
}
