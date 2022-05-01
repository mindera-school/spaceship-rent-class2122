package com.mindera.school.spaceshiprent.exception;

public final class ErrorMessageConstants {

    public static final String USER_NOT_FOUND = "Can't find any user with the id %s";
    public static final String SPACESHIP_NOT_FOUND = "Can't find any spaceship with the id %s";
    public static final String RENT_NOT_FOUND = "Can't find any rent with the id %s";

    public static final String USER_ALREADY_EXISTS = "User with given information already exists";

    public static final String USER_RENTS_NOT_FOUND = "User with id %s doesn't have any rents";

    public static final String SPACESHIP_UNAVAILABLE = "Spaceship with id %s is already rented between %s and %s";

    private ErrorMessageConstants() {
    }
}
