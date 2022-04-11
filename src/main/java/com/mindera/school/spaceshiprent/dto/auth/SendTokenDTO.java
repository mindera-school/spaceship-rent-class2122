package com.mindera.school.spaceshiprent.dto.auth;

import com.mindera.school.spaceshiprent.enumerator.UserType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendTokenDTO {
    private long id;
    private String email;
    private int age;
    private String name;
    private UserType userType;
    private String token;
}
