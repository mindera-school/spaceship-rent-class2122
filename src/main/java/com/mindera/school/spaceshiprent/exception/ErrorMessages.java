package com.mindera.school.spaceshiprent.exception;

public final class ErrorMessages {

    private ErrorMessages() {
    }

    public static final String USER_NOT_FOUND = "Can't find any user with the id %s";
    public static final String SPACESHIP_NOT_FOUND = "Can't find any Spaceship with the id %s";
    public static final String RENT_NOT_FOUND = "Can't find any rent with the id %s";
    public static final String RENT_NOT_FOUND_W_CUSTOMER = "Can't find any rent with the Customer id %s";
    public static final String RENT_NOT_FOUND_W_SPACESHIP = "Can't find any rent with the Spaceship id %s";
}
