package com.mindera.school.spaceshiprent.exception;

public abstract class SpaceshipRentException extends RuntimeException {

    public SpaceshipRentException() {
    }

    public SpaceshipRentException(String message) {
        super(message);
    }

    public SpaceshipRentException(String message, Throwable cause) {
        super(message, cause);
    }
}
