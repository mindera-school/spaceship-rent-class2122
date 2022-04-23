package com.mindera.school.spaceshiprent.exception;

public final class ErrorMessageConstants {

    public static final String USER_NOT_FOUND = "Can't find any user with the id %s";
    public static final String SPACESHIP_NOT_FOUND = "Can't find any spaceship with the id %s";
    public static final String RENT_NOT_FOUND = "Can't find any rent with the id %s";
    public static final String ACCOUNT_NOT_FOUND= "Account not Found";

    public static final String USER_RENTS_NOT_FOUND = "User with id %s doesn't have any rents";

    public static final String WRONG_CREDENTIALS = "Email and/or Password invalids";

    private ErrorMessageConstants() {
    }
}
