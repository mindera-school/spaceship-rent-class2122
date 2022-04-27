package com.mindera.school.spaceshiprent.exception.exceptions;

import com.mindera.school.spaceshiprent.exception.SpaceshipRentException;

public class InvalidApiKeyException extends SpaceshipRentException {

    public InvalidApiKeyException(String message) {
        super(message);
    }
}
