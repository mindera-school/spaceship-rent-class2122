package com.mindera.school.spaceshiprent.controller;

public final class Paths {
    public static final String PATH_CREATE_USER = "/users";
    public static final String PATH_GET_USERS = "/users";
    public static final String PATH_GET_USER_BY_ID = "/users/{id}";
    public static final String PATH_UPDATE_USER_BY_ID = "/users/{id}";

    public static final String PATH_POST_RENT = "/rents";
    public static final String PATH_GET_RENTS = "/rents";
    public static final String PATH_GET_RENT_BY_ID = "/rents/{id}";
    public static final String PATH_GET_RENTS_BY_CUSTOMER = "/customers/{customerId}/rents";
    public static final String PATH_GET_RENTS_BY_SPACESHIP = "/spaceships/{spaceshipId}/rents";
    public static final String PATH_UPDATE_RENT_BY_ID = "/rents/{id}";
    public static final String PATH_UPDATE_PICK_UP_DATE = "/rents/pickupdate/{id}";
    public static final String PATH_UPDATE_RETURN_DATE = "/rents/returndate/{id}";

    public static final String PATH_POST_SPACESHIP = "/spaceships";
    public static final String PATH_GET_SPACESHIPS = "/spaceships";
    public static final String PATH_GET_SPACESHIP_BY_ID = "/spaceships/{id}";
    public static final String PATH_UPDATE_SPACESHIP_BY_ID = "/spaceships/{id}";
}
