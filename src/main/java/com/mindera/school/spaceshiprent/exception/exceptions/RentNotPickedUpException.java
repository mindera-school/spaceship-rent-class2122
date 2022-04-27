package com.mindera.school.spaceshiprent.exception.exceptions;

import com.mindera.school.spaceshiprent.exception.SpaceshipRentException;

public class RentNotPickedUpException extends SpaceshipRentException {

    public RentNotPickedUpException(String message) {
        super(message);
    }
}
