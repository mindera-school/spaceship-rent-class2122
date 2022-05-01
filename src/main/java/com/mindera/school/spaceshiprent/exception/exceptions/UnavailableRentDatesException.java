package com.mindera.school.spaceshiprent.exception.exceptions;

import com.mindera.school.spaceshiprent.exception.SpaceshipRentException;

public class UnavailableRentDatesException extends SpaceshipRentException {

    public UnavailableRentDatesException(String message) {
        super(message);
    }
}
