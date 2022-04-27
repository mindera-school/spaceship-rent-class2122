package com.mindera.school.spaceshiprent.exception.exceptions;

import com.mindera.school.spaceshiprent.exception.SpaceshipRentException;

public class RentAlreadyReturnedException extends SpaceshipRentException {

    public RentAlreadyReturnedException(String message) {
        super(message);
    }
}
