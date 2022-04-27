package com.mindera.school.spaceshiprent.exception.exceptions;

import com.mindera.school.spaceshiprent.exception.SpaceshipRentException;

public class WrongCredentialsException extends SpaceshipRentException {

    public WrongCredentialsException(String message) {
        super(message);
    }
}
