package com.mindera.school.spaceshiprent.exception;

public final class ErrorMessages {

    private ErrorMessages() {}

    public static final String USER_NOT_FOUND = "Can't find any user with the id %s";
    public static final String SPACESHIP_NOT_FOUND = "Can't find any spaceship with the id %s";
    public static final String RENT_NOT_FOUND = "Can't find any rent with the id %s";

    public static final String USER_RENTS_NOT_FOUND = "User with id %s doesn't have any rents";
}
