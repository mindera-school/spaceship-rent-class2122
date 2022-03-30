package com.mindera.school.spaceshiprent.exception.NotFoundExceptions;

import com.mindera.school.spaceshiprent.exception.SpaceshipRentException;

public class UserNotFoundException extends SpaceshipRentException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
