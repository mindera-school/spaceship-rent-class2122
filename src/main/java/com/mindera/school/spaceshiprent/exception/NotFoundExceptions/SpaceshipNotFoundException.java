package com.mindera.school.spaceshiprent.exception.NotFoundExceptions;

import com.mindera.school.spaceshiprent.exception.SpaceshipRentException;

public class SpaceshipNotFoundException extends SpaceshipRentException {
    public SpaceshipNotFoundException(String message) {
        super(message);
    }
}
