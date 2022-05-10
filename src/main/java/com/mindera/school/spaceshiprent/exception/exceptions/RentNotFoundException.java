package com.mindera.school.spaceshiprent.exception.exceptions;

import com.mindera.school.spaceshiprent.exception.SpaceshipRentException;

public class RentNotFoundException extends SpaceshipRentException {
    public RentNotFoundException(String message) {
        super(message);
    }
}
