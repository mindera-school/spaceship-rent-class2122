package com.mindera.school.spaceshiprent.util;

import java.util.Map;

public class LoggerHelper {

    // information about type of request
    public static final String POST_REQUEST = "Request to create {}";
    public static final String GET_REQUEST = "Request to get {}";
    public static final String GET_ALL_REQUEST = "Request to get all {}s";
    public static final String GET_ALL_BY_REQUEST = "Request to get all {}s by {}";
    public static final String PUT_REQUEST = "Request to update {}";
    public static final String PATCH_REQUEST = "Request to {}";
    public static final String DELETE_REQUEST = "Request to delete {}";

    // specify the target entity of the request
    public static final String ID = "id";
    public static final String USER = "user";
    public static final String CUSTOMER = "customer";
    public static final String EMPLOYEE = "employee";
    public static final String SPACESHIP = "spaceship";
    public static final String RENT = "rent";
    public static final String RENT_PICKUP = "pickup rent";
    public static final String RENT_RETURN = "return rent";

    // other information
    public static final String COULD_NOT_FIND = "Couldn't find {}";

    // specifics
    public static final String RENT_ALREADY_PICKED_UP = "Rent already picked up";
    public static final String RENT_NOT_PICKED_UP = "Rent not picked up";
    public static final String RENT_ALREADY_RETURNED = "Rent already returned";

    private final Map<String, Object> fields;
    private String message;

    private LoggerHelper() {
        fields = new java.util.HashMap<>();
    }

    public static LoggerHelper newLogMessage() {
        return new LoggerHelper();
    }

    public LoggerHelper message(String message) {
        this.message = message;
        return this;
    }

    public LoggerHelper message(String message, Object... args) {
        this.message = mapPlaceHolders(message, args);
        return this;
    }

    public LoggerHelper userId(Long userId) {
        fields.put("userId", userId.toString());
        return this;
    }

    public LoggerHelper customerId(Long userId) {
        fields.put("customerId", userId.toString());
        return this;
    }

    public LoggerHelper spaceshipId(Long spaceshipId) {
        fields.put("spaceshipId", spaceshipId.toString());
        return this;
    }

    public LoggerHelper rentId(Long rentId) {
        fields.put("rentId", rentId.toString());
        return this;
    }

    public LoggerHelper field(String key, String value) {
        fields.put(key, value);
        return this;
    }

    public LoggerHelper field(String key, String value, Object... args) {
        fields.put(key, mapPlaceHolders(value, args));
        return this;
    }

    public String build() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            builder.append("[").append(entry.getKey())
                    .append(": ").append(entry.getValue()).append("]");
        }
        builder.append(" ").append(message);
        return builder.toString();
    }

    private String mapPlaceHolders(String string, Object... args) {
        String[] placeHolders = string.split("\\{}");
        if (placeHolders.length != args.length + 1) {
            throw new IllegalArgumentException("Number of placeholders does not match number of arguments");
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < placeHolders.length; i++) {
            builder.append(placeHolders[i]);
            if (i < args.length) {
                builder.append(args[i]);
            }
        }
        return builder.toString();
    }
}
