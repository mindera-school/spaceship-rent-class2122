package com.mindera.school.spaceshiprent.exception.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Builder
@Data
public class SpaceshipRentError {
    private Date timestamp;
    private String message;
    private String exception;
    private String path;
}
