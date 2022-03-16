package com.mindera.school.spaceshiprent.exception;

import lombok.Builder;

@Builder
public class SpaceshipError {

    private String message;
    private String exception;
    private String path;
}
