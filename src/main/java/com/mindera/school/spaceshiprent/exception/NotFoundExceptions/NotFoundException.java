package com.mindera.school.spaceshiprent.exception.NotFoundExceptions;

import com.mindera.school.spaceshiprent.exception.SpaceshipRentException;

public class NotFoundException extends SpaceshipRentException {

    public NotFoundException(String message) {
        super(message);
    }
}
