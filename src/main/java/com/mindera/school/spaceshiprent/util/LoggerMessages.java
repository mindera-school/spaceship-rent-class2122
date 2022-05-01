package com.mindera.school.spaceshiprent.util;

public final class LoggerMessages {
    private LoggerMessages() {
    }

    // information about type of request
    public static final String POST_REQUEST = "Request received to create {}";
    public static final String GET_REQUEST = "Request received to get {}";
    public static final String GET_ALL_REQUEST = "Request received to get all {}";
    public static final String PUT_REQUEST = "Request received to update {}";
    public static final String DELETE_REQUEST = "Request received to delete {}";

    // specify the target entity of the request
    public static final String USER = "user";
    public static final String SPACESHIP = "spaceship";
    public static final String RENT = "rent";

    // success messages
    public static final String CREATED = "Successfully created {}";

}
