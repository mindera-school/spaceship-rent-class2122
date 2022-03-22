package com.mindera.school.spaceshiprent.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpaceshipError {

    private String message;
    private String exception;
    private String path;
}
