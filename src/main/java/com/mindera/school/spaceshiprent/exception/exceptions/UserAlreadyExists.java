package com.mindera.school.spaceshiprent.exception.exceptions;

import com.mindera.school.spaceshiprent.exception.SpaceshipRentException;

public class UserAlreadyExists extends SpaceshipRentException {
    public UserAlreadyExists(String message) {
        super(message);
    }
}
