package com.mindera.school.spaceshiprent.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SpaceshipError {

    private String message;
    private String exception;
    private String path;
}
