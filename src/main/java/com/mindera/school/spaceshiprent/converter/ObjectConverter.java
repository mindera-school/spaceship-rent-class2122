package com.mindera.school.spaceshiprent.converter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ObjectConverter {

    public static HashMap<String,String> convertStringToObject(String stringObject) {
            HashMap<String,String> map = new HashMap<>();
            for(String keyValue: stringObject.split(", ")) {
                String[] parts = keyValue.split("=", 2);
                map.put(parts[0], parts[1]);
            }
            return map;
    }
}
