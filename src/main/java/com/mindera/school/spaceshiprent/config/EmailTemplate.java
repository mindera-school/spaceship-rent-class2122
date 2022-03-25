package com.mindera.school.spaceshiprent.config;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;

@Component
public class EmailTemplate {

    private static final String path = "htmltemplates/";

    @SneakyThrows
    public static String getBodyTemplate(String templateName) {
        StringBuilder template = new StringBuilder();
        URL url = EmailTemplate.class.getClassLoader().getResource(path+templateName+"/"+templateName+".html");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(url).openStream()));

        String line;
        while( (line = in.readLine()) != null) {
            template.append(line);
        }

        in.close();
        return template.toString();
    }

    @SneakyThrows
    public static String getSubjectTemplate(String templateName) {
        StringBuilder template = new StringBuilder();
        URL url = EmailTemplate.class.getClassLoader().getResource(path+templateName+"/"+templateName+".txt");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(url).openStream()));

        String line;
        while( (line = in.readLine()) != null) {
            template.append(line);
        }

        in.close();
        return template.toString();
    }
}
