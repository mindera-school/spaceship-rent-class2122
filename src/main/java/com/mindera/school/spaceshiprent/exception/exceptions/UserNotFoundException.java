package com.mindera.school.spaceshiprent.exception.exceptions;

import com.mindera.school.spaceshiprent.exception.SpaceshipRentException;

public class UserNotFoundException extends SpaceshipRentException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
