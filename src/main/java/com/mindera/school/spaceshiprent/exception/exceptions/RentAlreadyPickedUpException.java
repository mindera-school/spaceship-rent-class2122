package com.mindera.school.spaceshiprent.exception.exceptions;

import com.mindera.school.spaceshiprent.exception.SpaceshipRentException;

public class RentAlreadyPickedUpException extends SpaceshipRentException {

    public RentAlreadyPickedUpException(String message) {
        super(message);
    }
}
