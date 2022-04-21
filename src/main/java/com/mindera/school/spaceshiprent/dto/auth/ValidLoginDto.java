package com.mindera.school.spaceshiprent.dto.auth;

import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.enumerator.UserType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidLoginDto {
   private UserDetailsDto userDetails;
    private String token;
}
