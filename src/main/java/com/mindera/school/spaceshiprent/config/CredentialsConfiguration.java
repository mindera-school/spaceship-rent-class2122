package com.mindera.school.spaceshiprent.config;

import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;

@Component
public class CredentialsConfiguration {

    @SneakyThrows
    public static String getCredentials(String key) {
        JSONObject jsonObject = new JSONObject(readFile());
        return jsonObject.getString(key);
    }

    @SneakyThrows
    private static String readFile() {

        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(Objects.requireNonNull(CredentialsConfiguration.class.getClassLoader().getResource("credentials.json")).getFile()))) {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }
}
